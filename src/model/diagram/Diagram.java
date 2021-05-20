package model.diagram;

import model.Database;
import model.boxes.Box;
import global.point.ScaledPoint;
import model.boxes.BoxType;
import model.boxes.BoxFacade;
import model.relations.ArrowType;
import model.relations.Relation;

import java.util.ArrayList;
import java.util.List;

public class Diagram implements DiagramFacade, DiagramMediator, PathfindingMap {

    //TODO: fixa crash när två boxar flyttas över varandra
    //TODO: mycket mindre borde vara public över lag
    BoxGrid2 boxGrid = new BoxGrid2();
    RelationGrid relationGrid = new RelationGrid(new AStar(this));
    String name = "untitled";
    Boolean saveLocked = false;

    //region OBSERVABLE
    public ArrayList<DiagramObserver> observers = new ArrayList<>(); //TODO: ta bort public

    private void updateObservers(Box box) {
        observers.forEach(diagramObserver -> diagramObserver.addBox(box));
    }

    private void updateObservers(Relation relation) {
        observers.forEach(diagramObserver -> diagramObserver.addRelation(relation));
    }

    @Override
    public void subscribe(DiagramObserver diagramObserver) {
        observers.add(diagramObserver);
    }
    //endregion

    @Override
    public void createBox(ScaledPoint position, BoxType boxType) {
        Box box = new Box(this, position, boxType);
        boxGrid.add(box);
        updateBox(box);
        updateObservers(box);
        saveThis();
    }

    public void updateBox(Box box) {
        boxGrid.update(box);
        relationGrid.refreshAllPaths();
        saveThis();
    }

    @Override
    public void removeBox(Box box) {
        boxGrid.remove(box);
        relationGrid.removeDisconnectedRelations();
        relationGrid.refreshAllPaths();
        saveThis();
    }

    @Override
    public void createRelation(BoxFacade from, ScaledPoint offsetFrom, BoxFacade to, ScaledPoint offsetTo, ArrowType arrowType) {
        Relation relation = new Relation(this, from, offsetFrom, to, offsetTo, arrowType);
        relationGrid.add(relation);
        relationGrid.refreshAllPaths();
        updateObservers(relation);
        saveThis();
    }

    public void updateRelation(Relation relation) {
        relationGrid.refreshAllPaths();
        saveThis();
    }

    public void removeRelation(Relation relation) {
        relationGrid.remove(relation);
        relationGrid.refreshAllPaths();
        saveThis();
    }

    public List<Box> getAllBoxes() {
        return boxGrid.getBoxes();
    }

    public List<Relation> getAllRelations() {
        return relationGrid.getRelations();
    }

    @Override
    public boolean isOccupied(ScaledPoint position) {
        return boxGrid.isOccupied(position);
    }

    @Override
    public int moveCost(Relation relation, ScaledPoint position) {
        return relationGrid.crossCost(relation, position);
    }

    public void setName(String newName){
        name = newName;
    }

    public String getName(){
        return name;
    }

    private void saveThis(){
        if(!saveLocked) {
            Database.saveDiagram(this, "diagrams/", "");
            System.out.println("Saved diagram as " + name + ".uml");
        }
    }

    public void lockSaving(){
        saveLocked = true;
    }

    public void unlockSaving(){
        saveLocked = false;
    }
}