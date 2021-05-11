package model;

import model.boxes.Box;
import model.diagram.Diagram;
import model.diagram.DiagramFacade;
import model.facades.FileHandlerFacade;

public class Model implements ModelFacade, FileHandlerFacade {

    private static Model singleton;
    Diagram diagram = new Diagram();

    public static Model getModel() {
        if (singleton == null) singleton = new Model();
        return singleton;
    }

    @Override
    public DiagramFacade getDiagram() {
        return diagram;
    }






    @Override
    public String[] getAllFileNames() {
        return Database.getAllFileNames("diagrams/");
    }

    @Override
    public void loadFile(String fileName) {
        diagram = Database.loadDiagram("diagrams/", fileName);
        for (Box box : diagram.getAllBoxes()) {
            diagram.observers.forEach(diagramObserver -> diagramObserver.addBox(box));
        }
    }

    public void loadTemplate(String fileName){
        Diagram template = Database.loadDiagram("templates/", fileName);
        for(Box box : template.getAllBoxes()){
            diagram.observers.forEach(diagramObserver -> diagramObserver.addBox(box));
        }
    }

    @Override
    public void newFile() {
        diagram.setName(Database.newFile());
        if(diagram.getName() != null)
            loadFile(diagram.getName());
    }
}
