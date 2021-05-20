package model;

import global.point.ScaledPoint;
import model.boxes.Box;
import model.boxes.BoxFacade;
import model.boxes.BoxType;
import model.diagram.Diagram;
import model.diagram.ModelObserver;
import model.facades.FileHandlerFacade;
import model.facades.*;

import model.relations.ArrowType;
import model.relations.Relation;

import javax.xml.crypto.Data;
import java.io.File;
import java.util.ArrayList;

public class Model implements ModelFacade, FileHandlerFacade {

    private static Model singleton;
    Diagram diagram = new Diagram();

    public static Model getModel() {
        if (singleton == null) {
            singleton = new Model();
            singleton.init();
        }
        return singleton;
    }

    private void init(){
        diagram = new Diagram();
        diagram.setObserver(this);
        diagram.updateUndo();
    }

    //region OBSERVABLE
    private ArrayList<ModelObserver> observers = new ArrayList<>(); //TODO: ta bort public

    private void updateObservers(Box box) {
        observers.forEach(modelObserver -> modelObserver.addBox(box));
    }

    private void updateObservers(Relation relation) {
        observers.forEach(modelObserver -> modelObserver.addRelation(relation));
    }

    @Override
    public void subscribe(ModelObserver modelObserver) {
        observers.add(modelObserver);
    }
    //endregion

    @Override
    public void createBox(ScaledPoint position, BoxType boxType) {
        stopUndo();
        updateObservers(diagram.createBox(position, boxType));
        Database.saveDiagram(diagram, "diagrams/", "");
        resumeUndo();
        updateUndo();
    }

    @Override
    public void createRelation(BoxFacade from, ScaledPoint offsetFrom, BoxFacade to, ScaledPoint offsetTo, ArrowType arrowType) {
        stopUndo();
        updateObservers(diagram.createRelation(from, offsetFrom, to, offsetTo, arrowType));
        Database.saveDiagram(diagram, "diagrams/", "");
        resumeUndo();
        updateUndo();
    }


	/*public void addBox(ScaledPoint position, BoxType boxType) {
        saveUndoLayer();
        observers.forEach(observer -> observer.addBox(new Box(diagram, position, boxType)));
    }
	
	public void addRelation(BoxFacade from,ScaledPoint offsetFrom, BoxFacade to,ScaledPoint offsetTo, ArrowType arrowType) {
        //todo fix offset
        saveUndoLayer();
        Relation relation = new Relation(from, to, arrowType);
        diagram.add(relation);
        observers.forEach(observer -> observer.addRelation(relation));
    }
    //for merging an arrow into another arrow
    public void addRelation(BoxFacade from,ScaledPoint offsetFrom, RelationFacade followRelation) {
        //todo fix offset (steal offset from followrelation or something)

        //todo might need to save the data that this relation follows followRelation as viceversa
        // since i should not be able to change type of one and they stay merged
        Relation relation = new Relation(from, followRelation.getTo(), followRelation.getArrowType());
        diagram.add(relation);
        observers.forEach(observer -> observer.addRelation(relation));
    }
    public void removeRelation(RelationFacade relation){
        //todo
    }*/

    /**
     * returns the name of all .uml files in the "diagrams" folder.
     * @return
     */
    @Override
    public String[] getAllFileNames() {
        return Database.getAllFileNames("diagrams/");
    }

    /**
     * Loads in a diagram file, then replaces the current diagram with this one
     * @param fileName The name of the file we want to load
     */
    @Override
    public void loadFile(String fileName) {
        diagram = Database.loadDiagram("diagrams/", fileName, "");
        diagram.setObserver(this);
        stopUndo();
        for (Box box : diagram.getAllBoxes()) {
            observers.forEach(diagramObserver -> diagramObserver.addBox(box));
        }
        for (Relation relation : diagram.getAllRelations()){
            observers.forEach(diagramObserver -> diagramObserver.addRelation(relation)); //todo
        }
        resumeUndo();
        undoLayer = -1;
        redoLayer = 0;
        maxUndo = -1;
        updateUndo();
    }

    @Override
    public void saveAs(String name) {
        diagram.setName(name);
        Database.saveDiagram(diagram, "diagrams/", "");
    }

    @Override
    public void deleteFile(String name) {
        new File("diagrams/" + name + ".uml").delete();
    }

    //REGION templates
    @Override
    public String[] getAllTemplateNames(){
        return Database.getAllFileNames("templates/");
    }

    /**
     * Loads in a template file with the given name, then adds all boxes & relations to the current diagram
     * @param fileName The name of the file we want to load
     */
    @Override
    public void loadTemplate(String fileName){
        saveUndoLayer();
        stopUndo();
        Diagram template = Database.loadDiagram("templates/", fileName, "");
        for(Box box : template.getAllBoxes()){
            diagram.addBox(box);
            observers.forEach(diagramObserver -> diagramObserver.addBox(box));
        }
        for (Relation relation: template.getAllRelations()) {
            diagram.addRelation(relation);
            observers.forEach(diagramObserver -> diagramObserver.addRelation(relation)); //todo
        }
        resumeUndo();
        //TODO: Sätt i databasen: System.out.println("loaded template " + fileName);
    }

    @Override
    public void saveTemplate(String name) {
        diagram.setName(name);
        Database.saveDiagram(diagram, "templates/", "");
    }

    @Override
    public void deleteTemplate(String name) {
        new File("templates/" + name + ".uml").delete();
    }

    //endregion

    /**
     * Creates an empty file with the name "new" + the first unused number, then loads the empty file
     */
    @Override
    public void newFile() {
        diagram.setName(Database.newFile());
        if(diagram.getName() != null) { //TODO: Samma som förra
            loadFile(diagram.getName());
        }
    }


    //REGION Undo/Redo
    private int undoLayer = -1; //the suffix of the file to be loaded on undo
    private int redoLayer = 1; // the suffix of the file to be loaded on redo
    private int maxUndo = 0; //the highest existing relevant layer of undo
    private Boolean undoActive = true;

    @Override
    public void updateUndo() {
        if(undoActive)
            saveUndoLayer();
    }

    @Override
    public void stopUndo() {
        undoActive = false;
    }

    @Override
    public void resumeUndo() {
        undoActive = true;
    }

    private void saveUndoLayer(){
        if(!Database.directoryCheck("temp/")){
            File folder = new File("temp/");
            folder.mkdir();
        }
        if(maxUndo >= redoLayer) {
            maxUndo = redoLayer;
        } else{
            maxUndo++;
        }
        undoLayer = maxUndo - 1;
        redoLayer = maxUndo + 1;
        Database.saveDiagram(diagram, "temp/", Integer.toString(maxUndo));
        new File("temp/" + diagram.getName() + maxUndo + ".uml").deleteOnExit(); //should auto-delete temp-files when program closes
    }

    public void loadUndoLayer(){
        if(Database.directoryCheck("temp/") && canUndo()){
            stopUndo();
            new File("temp/" + diagram.getName() + (undoLayer + 1) + ".uml").deleteOnExit();
            diagram = Database.loadDiagram("temp/", diagram.getName(), Integer.toString(undoLayer));
            diagram.setObserver(this);
            for(Box box : diagram.getAllBoxes()){
                observers.forEach(Observer -> Observer.addBox(box));
            }
            for (Relation relation: diagram.getAllRelations()) {
                observers.forEach(Observer -> Observer.addRelation(relation));
            }
            undoLayer--;
            redoLayer--;
            resumeUndo();
        }
    }

    public void loadRedoLayer(){
        if(Database.directoryCheck("temp/") && canRedo()){
            stopUndo();
            diagram = Database.loadDiagram("temp/", diagram.getName(), Integer.toString(redoLayer));
            diagram.setObserver(this);
            for(Box box : diagram.getAllBoxes()){
                observers.forEach(diagramObserver -> diagramObserver.addBox(box));
            }
            for (Relation relation: diagram.getAllRelations()) {
                observers.forEach(diagramObserver -> diagramObserver.addRelation(relation));
            }
            undoLayer++;
            redoLayer++;
            resumeUndo();
        }
    }


    public Boolean canUndo(){
        return undoLayer >= 0;
    }

    public Boolean canRedo(){
        return redoLayer <= maxUndo;
    }
    //endregion

}
