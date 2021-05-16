package model.grid;

import model.Database;
import model.Model;
import model.boxes.Box;
import model.facades.DiagramObserver;
import model.point.ScaledPoint;
import model.relations.Relation;

import java.util.ArrayList;
import java.util.List;

public class Diagram implements IDiagram {

    //TODO: update database whenever diagram is updated

    BoxGrid2 boxGrid = new BoxGrid2();
    RelationGrid relationGrid = new RelationGrid(this);
    String name = "untitled";
    Boolean saveLocked = false;
    DiagramObserver observer;

    @Override
    public void add(Box box) {
        updateObserver();
        boxGrid.add(box);
        relationGrid.refreshAllPaths();
        saveThis();
    }

    @Override
    public void update(Box box) {
        updateObserver();
        boxGrid.update(box);
        relationGrid.refreshAllPaths();
        saveThis();
    }

    @Override
    public void remove(Box box) {
        updateObserver();
        boxGrid.remove(box);
        relationGrid.refreshAllPaths();
        saveThis();
    }

    @Override
    public void add(Relation relation) {
        updateObserver();
        relationGrid.add(relation);
        relationGrid.refreshAllPaths();
        saveThis();
    }

    @Override
    public void update(Relation relation) {
        updateObserver();
        relationGrid.refreshAllPaths();
        saveThis();
    }

    @Override
    public void remove(Relation relation) {
        updateObserver();
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

    public void setObserver(DiagramObserver observer) {
        this.observer = observer;
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

    private void updateObserver(){
        if(!saveLocked){
            observer.updateUndo();
        }
    }

    public void lockSaving(){
        saveLocked = true;
    }

    public void unlockSaving(){
        saveLocked = false;
    }
}