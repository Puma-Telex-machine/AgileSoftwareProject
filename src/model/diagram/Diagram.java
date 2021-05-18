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

    /**
     * 1. Creates a new box.
     * 2. Adds the box to the list of boxes. (Does not check position validity)
     * 3. Runs updateBox.
     * 4. Tells observers there is a new box.
     * @param position The preliminary position of the box
     * @param boxType The type of box
     */
    @Override
    public void createBox(ScaledPoint position, BoxType boxType) {
        Box box = new Box(this, position, boxType);
        boxGrid.add(box);
        updateBox(box);
        updateObservers(box);
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
    }

    @Override
    public void removeBox(Box box) {
        boxGrid.remove(box);
        relationGrid.refreshAllPaths();
        saveThis();
    }

    @Override
    public void createRelation(BoxFacade from, ScaledPoint offsetFrom, BoxFacade to, ScaledPoint offsetTo, ArrowType arrowType) {
        Relation relation = new Relation(this, from, offsetFrom, to, offsetTo, arrowType);
        relationGrid.add(relation);
        relationGrid.refreshAllPaths();
        updateObservers(relation);
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