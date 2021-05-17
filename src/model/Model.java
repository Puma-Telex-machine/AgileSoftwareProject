package model;

import model.boxes.Box;
import model.boxes.BoxType;
import model.facades.*;

import model.grid.Diagram;
import model.point.ScaledPoint;
import model.relations.ArrowType;
import model.relations.Relation;

import java.util.ArrayList;
import java.util.List;

public class Model implements ModelFacade, FileHandlerFacade {

    private static Model singleton;
    
    /** 
     * Creates a singelton that ensures that a model exists and returns it
     * @return Model
     */
    public static Model getModel() {
        if (singleton == null) singleton = new Model();
        return singleton;
    }

    ArrayList<Observer> observers = new ArrayList<>();
    Diagram diagram = new Diagram();

    
    /** 
     * Returns the model
     * @return FileHandlerFacade
     */
    @Override
    public FileHandlerFacade getFileHandler() {
        return getModel();
    }

    
    /** 
     * Add an observer to the lsit of observers
     * @param observer
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    
    /** 
     * Removes an observer from the list of observers
     * @param observer
     */
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

	
    /** 
     * To the list of observers, add the box for each observer
     * @param position
     * @param boxType
     */
    public void addBox(ScaledPoint position, BoxType boxType) {
        observers.forEach(observer -> observer.addBox(new Box(diagram, position, boxType)));
    }
	
	
    /** 
     * To the list of observers, add the relation for each observer
     * In the diagram, add the relation
     * @param from
     * @param offsetFrom
     * @param to
     * @param offsetTo
     * @param arrowType
     */
    public void addRelation(BoxFacade from,ScaledPoint offsetFrom, BoxFacade to,ScaledPoint offsetTo, ArrowType arrowType) {
        //todo fix offset
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
    }

    
    /** 
     * Gets the name of all files
     * @return String[]
     */
    @Override
    public String[] getAllFileNames() {
        return Database.getAllFileNames("diagrams/");
    }

    
    /** 
     * Loads a file
     * @param fileName
     */
    @Override
    public void loadFile(String fileName) {
        diagram = Database.loadDiagram("diagrams/", fileName);
        for (Box box : diagram.getAllBoxes()) {
            observers.forEach(observer -> observer.addBox(box));
        }
        for (Relation relation : diagram.getAllRelations()){
            observers.forEach(observer -> observer.addRelation(relation));
        }
    }

    
    /** 
     * Loads a template
     * @param fileName
     */
    public void loadTemplate(String fileName){
        Diagram template = Database.loadDiagram("templates/", fileName);
        for(Box box : template.getAllBoxes()){
            observers.forEach(observer -> observer.addBox(box));
        }
        for (Relation relation: template.getAllRelations()) {
            observers.forEach(observer -> observer.addRelation(relation));
        }
        //TODO: Sätt i databasen: System.out.println("loaded template " + fileName);
    }

    @Override
    public void newFile() {
        diagram.setName(Database.newFile());
        if(diagram.getName() != null) //TODO: Samma som förra
            loadFile(diagram.getName());
    }

    public List<Relation> getRelations(BoxFacade box){
        //todo
        return new ArrayList<>();
    }

}
