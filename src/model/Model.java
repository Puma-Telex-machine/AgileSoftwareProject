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
        for (Relation relation : diagram.getAllRelations()){
            observers.forEach(observer -> observer.addRelation(relation));
        }
    }

    public void loadTemplate(String fileName){
        Diagram template = Database.loadDiagram("templates/", fileName);
        for(Box box : template.getAllBoxes()){
            diagram.observers.forEach(diagramObserver -> diagramObserver.addBox(box));
        }
        for (Relation relation: template.getAllRelations()) {
            observers.forEach(observer -> observer.addRelation(relation));
        }
        //TODO: Sätt i databasen: System.out.println("loaded template " + fileName);
    }

    @Override
    public void newFile() {
        diagram.setName(Database.newFile());
        if(diagram.getName() != null)
            loadFile(diagram.getName());
    }
}
