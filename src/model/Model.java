package model;


import model.boxes.Box;
import model.facades.FileHandlerFacade;
import model.facades.ModelFacade;

import java.awt.*;
import java.util.ArrayList;

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

    public void addBox(Point position) {
        BoxManager boxManager = new BoxManager(diagram, position);
        if (!boxManager.isEmpty()) {
            observers.forEach(observer -> observer.addBox(boxManager));
        }
        Database.saveDiagram(diagram, name);
        System.out.println("saved "+name);
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
}
