package model.diagram;

import model.Database;
import model.boxes.Box;
import global.point.ScaledPoint;
import model.boxes.BoxType;
import model.boxes.BoxFacade;
import model.grid.BoxGrid2;
import model.grid.RelationGrid;
import model.relations.ArrowType;
import model.relations.Relation;

import java.util.ArrayList;
import java.util.List;

public class Diagram implements IDiagram, DiagramFacade {

    //TODO: gör att mycket mindre är public över lag
    BoxGrid2 boxGrid = new BoxGrid2();
    RelationGrid relationGrid = new RelationGrid(this);
    String name = "untitled";
    Boolean saveLocked = false;
    public ArrayList<DiagramObserver> observers = new ArrayList<>(); //TODO: ta bort public

    @Override
    public void subscribe(DiagramObserver diagramObserver) {
        observers.add(diagramObserver);
    }

    @Override
    public void createBox(ScaledPoint position, BoxType boxType) {
        Box box = new Box(this, position, boxType);
        boxGrid.add(box);
        observers.forEach(diagramObserver -> diagramObserver.addBox(box));
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
        relationGrid.refreshAllPaths();
        saveThis();
    }

    @Override
    public void createRelation(BoxFacade from, ScaledPoint offsetFrom, BoxFacade to, ScaledPoint offsetTo, ArrowType arrowType) {
        Relation relation = new Relation(this, from, to, arrowType);
        relationGrid.add(relation);
        relationGrid.refreshAllPaths();
        observers.forEach(diagramObserver -> diagramObserver.addRelation(relation));
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
    public boolean canMergeLines(Relation relation, ScaledPoint position) {
        return relationGrid.canMergeLines(relation, position);
    }

    public void setName(String newName){
        name = newName;
    }

    public String getName(){
        return name;
    }

    private void saveThis(){
        if(!saveLocked) {
            Database.saveDiagram(this);
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