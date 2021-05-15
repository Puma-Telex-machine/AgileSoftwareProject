package model;

import model.boxes.Box;
import model.boxes.BoxType;
import model.facades.*;

import model.grid.Diagram;
import model.point.ScaledPoint;
import model.relations.ArrowType;
import model.relations.Relation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Model implements ModelFacade, FileHandlerFacade {

    private static Model singleton;
    public static Model getModel() {
        if (singleton == null) singleton = new Model();
        return singleton;
    }

    ArrayList<Observer> observers = new ArrayList<>();
    Diagram diagram = new Diagram();
    private int undoLayer = 0; //the suffix added to the next temporary file (?)
    private int redoLayer = -1; // the suffix of the file to be loaded on redo (?)

    @Override
    public FileHandlerFacade getFileHandler() {
        return getModel();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

	public void addBox(ScaledPoint position, BoxType boxType) {
        observers.forEach(observer -> observer.addBox(new Box(diagram, position, boxType)));
    }
	
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
        diagram = Database.loadDiagram("diagrams/", fileName);
        for (Box box : diagram.getAllBoxes()) {
            observers.forEach(observer -> observer.addBox(box));
        }
        for (Relation relation : diagram.getAllRelations()){
            observers.forEach(observer -> observer.addRelation(relation));
        }
    }

    /**
     * Loads in a template file with the given name, then adds all boxes & relations to the current diagram
     * @param fileName The name of the file we want to load
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

    /**
     * Creates an empty file with the name "new" + the first unused number, then loads the empty file
     */
    @Override
    public void newFile() {
        diagram.setName(Database.newFile());
        if(diagram.getName() != null) //TODO: Samma som förra
            loadFile(diagram.getName());
    }

    private void saveUndoLayer(){
        /*if(!Database.directoryCheck("temp/")){
            File folder = new File("temp/");
            folder.mkdir();
            Database.saveDiagram(diagram, "temp/", Integer.toString(undoLayer));
            new File("temp/" + diagram.getName() + ".uml").deleteOnExit(); //auto-deletes temp-files when program closes (normally)
            undoLayer++;
        }*/
    }

    /**
     * get all relations this box interacts with
     */
    public List<Relation> getRelations(BoxFacade box){
        //todo
        return new ArrayList<>();
    }

}
