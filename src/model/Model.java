package model;

import global.point.Scale;
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

    
    /** 
     * Creates a singleton that ensures that a model exists and returns it
     * @return Model
     */
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

    
    /** 
     * Returns the model
     * @return FileHandlerFacade
     */
    @Override
    public void subscribe(ModelObserver modelObserver) {
        observers.add(modelObserver);
    }
    //endregion


	/** 
     * Create a box and add it to all observers
     * @param position
     * @param boxType
     */
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


    /**
     * returns the name of all .uml files in the "diagrams" folder.
     * @return the name of all saved diagrams
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
        diagram = new Diagram();
        diagram.setObserver(this);
        diagram.setName(fileName);
        saveUndoLayer(); //creates empty first tempfile
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
        undoLayer = 0;
        redoLayer = 1;
        maxUndo = 0;
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
        stopUndo();
        Diagram template = Database.loadDiagram("templates/", fileName, "");
        for(Box box : template.getAllBoxes()){
            box.setDiagram(diagram);
            diagram.addBox(box);
            observers.forEach(diagramObserver -> diagramObserver.addBox(box));
        }
        for (Relation relation: template.getAllRelations()) {
            relation.setDiagram(diagram);
            diagram.addRelation(relation);
            observers.forEach(diagramObserver -> diagramObserver.addRelation(relation)); //todo
        }
        resumeUndo();
        saveUndoLayer();
        //TODO: S??tt i databasen: System.out.println("loaded template " + fileName);
    }

    @Override
    public void saveTemplate(String name) {
        diagram.setName(name);
        Database.saveDiagram(diagram, "templates/", "");
    }

    @Override
    public void deleteTemplate(String name) {
        System.out.println(new File("templates/" + name + ".uml").delete());
    }

    //endregion

    /**
     * Creates an empty file with the name "new" + the first unused number, then loads the empty file
     */
    @Override
    public void newFile() {
        diagram.setName(Database.newFile());
        if(diagram.getName() != null) { //TODO: Samma som f??rra
            observers.forEach(ModelObserver::clearCanvas); //???
            loadFile(diagram.getName());
        }
    }

    //REGION Undo/Redo
    private int undoLayer = 0; //the suffix of the file to be loaded on undo
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
            System.out.println("loading undolayer" + undoLayer);
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
            System.out.println("loading redolayer" + redoLayer);
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
        return undoLayer > 0;
    }

    public Boolean canRedo(){
        return redoLayer <= maxUndo;
    }
    //endregion

    //Region copy/paste
    public void copy(BoxFacade[] boxes){
        diagram.lockSaving();
        int x = findLowestX(boxes);
        int y = findLowestY(boxes);
        reducePositions(x,y,boxes);
        Diagram temp = new Diagram();
        temp.setName("clipboard");
        temp.lockSaving();
        for (BoxFacade box: boxes) {
            temp.addBox((Box) box);
        }
        Database.saveDiagram(temp, "", "");
        new File("clipboard.uml").deleteOnExit();
        System.out.println("saved to clipboard");
        reducePositions(x*-1, y*-1, boxes);
        diagram.unlockSaving();
    }

    public void paste(ScaledPoint position){
        if(new File("clipboard.uml").exists()) {
            Diagram temp = Database.loadDiagram("", "clipboard", "");
            temp.lockSaving();
            for (Box box : temp.getAllBoxes()) {
                int newX = position.getX(Scale.Backend) + box.getPosition().getX(Scale.Backend);
                int newY = position.getY(Scale.Backend) + box.getPosition().getY(Scale.Backend);
                box.setPosition(new ScaledPoint(Scale.Backend, newX, newY));
                diagram.addBox(box);
                box.setDiagram(diagram);
                observers.forEach(diagramObserver -> diagramObserver.addBox(box));
            }
        }
    }

    public int findLowestX(BoxFacade[] boxes){
        int result = -1;
        for (BoxFacade box: boxes) {
            int current = box.getPosition().getX(Scale.Backend);
            if(result == -1)
                result = current;
            if(current < result){
                result = current;
            }
        }
        return result;
    }

    public int findLowestY(BoxFacade[] boxes){
        int result = -1;
        for (BoxFacade box: boxes) {
            int current = box.getPosition().getY(Scale.Backend);
            if(result == -1)
                result = current;
            if(current < result){
                result = current;
            }
        }
        return result;
    }

    public void reducePositions(int x, int y, BoxFacade[] boxes){
        for (BoxFacade box: boxes) {
            box.setPosition(new ScaledPoint(Scale.Backend, box.getPosition().getX(Scale.Backend)-x, box.getPosition().getY(Scale.Backend)-y));
        }
    }
    //endregion
}
