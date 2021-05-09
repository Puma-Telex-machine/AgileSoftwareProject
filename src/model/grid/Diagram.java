package model.grid;

import model.Database;
import model.boxes.Box;
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

    @Override
    public void update(Box box) {
        boxGrid.update(box);
        relationGrid.refreshAllPaths();
        saveThis();
    }

    @Override
    public void remove(Box box) {
        boxGrid.remove(box);
        relationGrid.refreshAllPaths();
        saveThis();
    }

    public ArrayList<Box> getAllBoxes() {
        return boxGrid.getBoxes();
    }

    public void update(Relation relation) {
        relationGrid.add(relation);
        relationGrid.refreshAllPaths();     // Detta gör om arbetet i add, det beror på vilken funktionalitet vi vill ha.
        saveThis();                         // Hur "känns det" när alla relationer uppdateras är väl det som är viktigast.
    }

    @Override
    public void remove(Relation relation) {
        relationGrid.remove(relation);
        relationGrid.refreshAllPaths();
        saveThis();
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