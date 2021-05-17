package model;

import model.boxes.Box;
import model.diagram.Diagram;
import model.diagram.DiagramFacade;
import model.facades.FileHandlerFacade;
import model.boxes.BoxType;
import model.facades.*;

import model.relations.ArrowType;
import model.relations.Relation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Model implements ModelFacade, FileHandlerFacade, DiagramObserver {

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
        //diagram.setObserver(this);
    }

    //ArrayList<Observer> observers = new ArrayList<>();

    @Override
    public DiagramFacade getDiagram() {
        return diagram;
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
        //diagram.setObserver(this);
        for (Box box : diagram.getAllBoxes()) {
            diagram.observers.forEach(diagramObserver -> diagramObserver.addBox(box));
        }
        for (Relation relation : diagram.getAllRelations()){
            diagram.observers.forEach(diagramObserver -> diagramObserver.addRelation(relation)); //todo
        }
        undoLayer = -1;
        redoLayer = 0;
        maxUndo = -1;
    }

    /**
     * Loads in a template file with the given name, then adds all boxes & relations to the current diagram
     * @param fileName The name of the file we want to load
     */
    public void loadTemplate(String fileName){
        saveUndoLayer();
        Diagram template = Database.loadDiagram("templates/", fileName, "");
        for(Box box : template.getAllBoxes()){
            diagram.observers.forEach(diagramObserver -> diagramObserver.addBox(box));
        }
        for (Relation relation: template.getAllRelations()) {
            diagram.observers.forEach(diagramObserver -> diagramObserver.addRelation(relation)); //todo
        }
        //TODO: Sätt i databasen: System.out.println("loaded template " + fileName);
    }

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

    private int undoLayer = -1; //the suffix of the file to be loaded on undo
    private int redoLayer = 0; // the suffix of the file to be loaded on redo
    private int maxUndo = -1; //the highest existing relevant layer of undo

    @Override
    public void updateUndo() {
        saveUndoLayer();
    }

    private void saveUndoLayer(){
        if(!Database.directoryCheck("temp/")){
            File folder = new File("temp/");
            folder.mkdir();
        }
        if(maxUndo > redoLayer) {
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
            Database.saveDiagram(diagram, "temp/", Integer.toString(undoLayer + 1));
            new File("temp/" + diagram.getName() + (undoLayer + 1) + ".uml").deleteOnExit();
            diagram = Database.loadDiagram("temp/", diagram.getName(), Integer.toString(undoLayer));
            //diagram.setObserver(this);
            for(Box box : diagram.getAllBoxes()){
                diagram.observers.forEach(diagramObserver -> diagramObserver.addBox(box));
            }
            for (Relation relation: diagram.getAllRelations()) {
                diagram.observers.forEach(diagramObserver -> diagramObserver.addRelation(relation));
            }
            undoLayer--;
            redoLayer--;
        }
    }

    public void loadRedoLayer(){
        if(Database.directoryCheck("temp/") && canRedo()){
            diagram = Database.loadDiagram("temp/", diagram.getName(), Integer.toString(redoLayer));
            //diagram.setObserver(this);
            for(Box box : diagram.getAllBoxes()){
                diagram.observers.forEach(diagramObserver -> diagramObserver.addBox(box));
            }
            for (Relation relation: diagram.getAllRelations()) {
                diagram.observers.forEach(diagramObserver -> diagramObserver.addRelation(relation));
            }
            undoLayer++;
            redoLayer++;
        }
    }

    public Boolean canUndo(){
        return undoLayer >= 0;
    }

    public Boolean canRedo(){
        return redoLayer <= maxUndo;
    }


}
