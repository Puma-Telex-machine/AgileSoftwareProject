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
    public static Model getModel() {
        if (singleton == null) singleton = new Model();
        return singleton;
    }

    ArrayList<Observer> observers = new ArrayList<>();
    Diagram diagram = new Diagram();

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
        Relation relation = new Relation(from, to, arrowType);
        diagram.add(relation);
        observers.forEach(observer -> observer.addRelation(relation));
    }
    public void removeRelation(RelationFacade relation){
        //todo
    }

    @Override
    public String[] getAllFileNames() {
        return Database.getAllFileNames("diagrams/");
    }

    @Override
    public void loadFile(String fileName) {
        diagram = Database.loadDiagram("diagrams/", fileName);
        for (Box box : diagram.getAllBoxes()) {
            observers.forEach(observer -> observer.addBox(box));
        }
        //TODO: Sätt i databasen: System.out.println("loaded " + name);
    }

    public void loadTemplate(String fileName){
        Diagram template = Database.loadDiagram("templates/", fileName);
        for(Box box : template.getAllBoxes()){
            observers.forEach(observer -> observer.addBox(box));
        }
        //TODO: Sätt i databasen: System.out.println("loaded template " + fileName);
    }

    @Override
    public void newFile() {
        diagram.setName(Database.newFile());
        if(diagram.getName() != null) //TODO: Samma som förra
            loadFile(diagram.getName());
    }

    /**
     * get all relations this box interacts with
     */
    public List<Relation> getRelations(BoxFacade box){
        //todo
        return new ArrayList<>();
    }

}
