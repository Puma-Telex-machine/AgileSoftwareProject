package model;




import model.boxes.Box;
import model.facades.FileHandlerFacade;
import model.facades.ModelFacade;

import java.awt.*;
import model.facades.BoxFacade;
import model.facades.Observer;
import model.facades.RelationFacade;
import model.grid.Diagram;
import model.point.ScaledPoint;
import model.relations.ArrowType;
import model.relations.Relation;

import java.util.ArrayList;
import java.util.List;

public class Model implements ModelFacade, FileHandlerFacade{

    private static Model singleton;
    public static Model getModel() {
        if (singleton == null) singleton = new Model();
        return singleton;
    }

    ArrayList<Observer> observers = new ArrayList<>();
    Diagram diagram = new Diagram();
    String name = "untitled"; //name of the currently opened file/diagram

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

	public void addBox(ScaledPoint position) {
        BoxFacade boxManager = diagram.createBox(position);
        observers.forEach(observer -> observer.addBox(boxManager));
		Database.saveDiagram(diagram, name);
        System.out.println("saved "+name);
    }
	
	public void addRelation(BoxFacade from, BoxFacade to, ArrowType arrowType) {
        RelationFacade relation = diagram.createRelation(from, to, arrowType);
        observers.forEach(observer -> observer.addRelation(relation));
    }

    @Override
    public String[] getAllFileNames() {
        return Database.getAllFileNames();
    }

    @Override
    public void loadFile(String fileName) {
        diagram = Database.loadDiagram(fileName);
        if(diagram != null) {
            for (Box box : diagram.boxGrid.getAllBoxes()) {
                BoxManager boxManager = new BoxManager(diagram, box);
                observers.forEach(observer -> observer.addBox(boxManager));
            }
            name = fileName;
            System.out.println("loaded " + name);
        }
    }

    @Override
    public void newFile() {
        name = Database.newFile();
        if(name != null)
            loadFile(name);
    }
    public Relation addRelation(BoxFacade from, BoxFacade to){
        //todo add relation and return the apropriate type
        return new Relation(null,null,ArrowType.ASSOCIATION);
    }
    public void changeRelation(Relation relation,ArrowType type){
        //todo change relation
    }

    public List<Point> getArrowBends(BoxFacade from, BoxFacade to) {
        //todo add pathfinding to here
        return new ArrayList<>();
    

    
}
