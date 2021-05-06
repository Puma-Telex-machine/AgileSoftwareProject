package model;

import model.boxes.Box;
import model.boxes.BoxType;
import model.facades.FileHandlerFacade;
import model.facades.ModelFacade;

import model.facades.BoxFacade;
import model.facades.Observer;
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
        observers.forEach(observer -> observer.addBox(new Box(diagram, position, boxType)));
		//TODO: Sätt i diagram: Database.saveDiagram(diagram, name);
        //TODO: Sätt i databasen: System.out.println("Saved diagram as: " + name);
    }
	
	public void addRelation(BoxFacade from, BoxFacade to, ArrowType arrowType) {
        Relation relation = new Relation(from, to, arrowType);
        diagram.set(relation);
        observers.forEach(observer -> observer.addRelation(relation));
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
        //TODO: Sätt i databasen: name = fileName;
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
        name = Database.newFile();
        if(name != null) //TODO: Samma som förra
            loadFile(name);
    }
}
