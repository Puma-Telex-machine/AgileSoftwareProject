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

    
    /** 
     * Adds a box to the boxGrid
     * @param box
     */
    @Override
    public void add(Box box) {
        boxGrid.add(box);
        relationGrid.refreshAllPaths();
        saveThis();
    }

    
    /** 
     * Updates a box on the boxGrid
     * @param box
     */
    @Override
    public void update(Box box) {
        boxGrid.update(box);
        relationGrid.refreshAllPaths();
        saveThis();
    }

    
    /** 
     * Remove a box from the boxGrid
     * @param box
     */
    @Override
    public void remove(Box box) {
        boxGrid.remove(box);
        relationGrid.refreshAllPaths();
        saveThis();
    }

    
    /** 
     * Add a realtion to the relationGrid
     * @param relation
     */
    @Override
    public void add(Relation relation) {
        relationGrid.add(relation);
        relationGrid.refreshAllPaths();
        saveThis();
    }

    
    /** 
     * Updates a relation on the relationGrid
     * @param relation
     */
    @Override
    public void update(Relation relation) {
        relationGrid.refreshAllPaths();
        saveThis();
    }

    
    /** 
     * Removes a relation from the relationGrid
     * @param relation
     */
    @Override
    public void remove(Relation relation) {
        relationGrid.remove(relation);
        relationGrid.refreshAllPaths();
        saveThis();
    }

    
    /** 
     * Get a list of all the boxes in the boxGrid
     * @return List<Box>
     */
    public List<Box> getAllBoxes() {
        return boxGrid.getBoxes();
    }

    
    /** 
     * Get a list of all the relations in the relationGrid
     * @return List<Relation>
     */
    public List<Relation> getAllRelations() {
        return relationGrid.getRelations();
    }

    
    /** 
     * Checks if a position in the boxGrid is occupied
     * @param position
     * @return boolean
     */
    @Override
    public boolean isOccupied(ScaledPoint position) {
        return boxGrid.isOccupied(position);
    }

    
    /** 
     * Checks if two lines can merge, based on their postion and relation
     * @param relation
     * @param position
     * @return boolean
     */
    @Override
    public boolean canMergeLines(Relation relation, ScaledPoint position) {
        return relationGrid.canMergeLines(relation, position);
    }

    
    /** 
     * set the name for the diagram
     * @param newName
     */
    public void setName(String newName){
        name = newName;
    }

    
    /** 
     * get the name of the diagram
     * @return String
     */
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