package model.diagram;

import model.Database;
import model.boxes.Box;
import global.point.ScaledPoint;
import model.boxes.BoxType;
import model.boxes.BoxFacade;
import model.facades.UndoChain;
import model.relations.ArrowType;
import model.relations.Relation;

import java.util.List;

public class Diagram implements DiagramMediator, PathfindingMap, UndoChain {

    //TODO: fixa crash när två boxar flyttas över varandra
    //TODO: mycket mindre borde vara public över lag
    BoxGrid2 boxGrid = new BoxGrid2();
    RelationGrid relationGrid = new RelationGrid(new AStar(this));
    String name = "untitled";
    Boolean saveLocked = false;
    UndoChain model;

    /**
     * 1. Creates a new box.
     * 2. Adds the box to the list of boxes. (Does not check position validity)
     * 3. Runs updateBox.
     * 4. Tells observers there is a new box.
     * @param position The preliminary position of the box
     * @param boxType The type of box
     */
    public Box createBox(ScaledPoint position, BoxType boxType) {
        Box box = new Box(this, position, boxType);
        boxGrid.add(box);
        updateBox(box);
        return box;
    }

    /**
     * Adds a preexisting box to the diagram and runs updateBox.
     * Used by the template system.
     * @param box the box to be added
     */
    public void addBox(Box box){
        boxGrid.add(box);
        updateBox(box);
    }

    /**
     * 1. Updates the box in the boxGrid which validates its position against all other boxes and set the position.
     *    (Does not update observers, this currently happens from inside the box when something happens or when creating a box)
     * 2. Refreshes all paths.
     * 3. Saves the diagram.
     * @param box the box to be updated
     */
    public void updateBox(Box box) {
        boxGrid.update(box);
        relationGrid.refreshAllPaths();
        saveThis();
        updateUndo();
    }

    @Override
    public void removeBox(Box box) {
        boxGrid.remove(box);
        relationGrid.removeDisconnectedRelations();
        relationGrid.refreshAllPaths();
        saveThis();
        updateUndo();
    }

    public Relation createRelation(BoxFacade from, ScaledPoint offsetFrom, BoxFacade to, ScaledPoint offsetTo, ArrowType arrowType) {
        Relation relation = new Relation(this, from, offsetFrom, to, offsetTo, arrowType);
        relationGrid.add(relation);
        relationGrid.refreshAllPaths();
        updateUndo();
        return relation;
    }

    public void addRelation(Relation relation){
        relationGrid.add(relation);
        relationGrid.refreshAllPaths();
        updateUndo();
    }

    public void updateRelation(Relation relation) { //todo varför behöver vi en Relation för att calla den här?
        relationGrid.refreshAllPaths();
        saveThis();
        updateUndo();
    }

    public void removeRelation(Relation relation) {
        relationGrid.remove(relation);
        relationGrid.refreshAllPaths();
        saveThis();
        updateUndo();
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

    private Boolean undoActive = true;

    public void setObserver(UndoChain observer){
        model = observer;
    }

    @Override
    public void updateUndo(){
        if(!saveLocked && undoActive){
            model.updateUndo();
        }
    }

    @Override
    public void stopUndo() {
        undoActive = false;
    }

    @Override
    public void resumeUndo() {
        undoActive = true;
    }
}