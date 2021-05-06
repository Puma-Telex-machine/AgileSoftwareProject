package model;

import model.boxes.Box;
import model.boxes.BoxType;
import model.facades.FileHandlerFacade;
import model.facades.ModelFacade;

import model.facades.BoxFacade;
import model.facades.Observer;
import model.grid.BoxManager;
import model.grid.Diagram;
import model.point.ScaledPoint;
import model.relations.ArrowType;
import model.relations.Relation;

import java.util.ArrayList;

public class Model implements ModelFacade, FileHandlerFacade {

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

	public void addBox(ScaledPoint position, BoxType boxType) {
        observers.forEach(observer -> observer.addBox(new Box(diagram, "test", position, boxType)));
		Database.saveDiagram(diagram, name);
        System.out.println("Saved diagram as: " + name);
    }
	
	public void addRelation(BoxFacade from, BoxFacade to, ArrowType arrowType) {
        Relation relation = new Relation(from, to, arrowType);
        diagram.add(relation);
        observers.forEach(observer -> observer.addRelation(relation));
    }

    @Override
    public String[] getAllFileNames() {
        return Database.getAllFileNames();
    }

    @Override
    public void loadFile(String fileName) {
        //TODO: Behöver vi spara innan detta händer eller e det automatiskt lugnt?
        diagram = Database.loadDiagram(fileName);
        for (Box box : diagram.boxGrid.getAllBoxes()) {
            BoxManager boxManager = new BoxManager(diagram.boxGrid, box);
            observers.forEach(observer -> observer.addBox(boxManager));
        }
        name = fileName;
        System.out.println("loaded " + name);
    }

    @Override
    public void newFile() {
        name = Database.newFile();
        if(name != null) //TODO: Samma som förra
            loadFile(name);
    }
}
