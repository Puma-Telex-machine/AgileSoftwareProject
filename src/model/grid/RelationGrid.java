package model.grid;

import model.boxes.Box;
import model.point.ScaledPoint;
import model.relations.Relation;

import java.util.*;

public class RelationGrid {

    AStar aStar;
    TreeMap<ScaledPoint, HashSet<PathNode>> relationMap;
    HashSet<Relation> relations;

    public RelationGrid(IDiagram diagram) {
        this.aStar = new AStar(diagram);
        this.relationMap = new TreeMap<>();
        this.relations = new HashSet<>();
    }

    
    /** 
     * adds a realtion to a hashset of realtions
     * @param relation
     */
    public void add(Relation relation) {
        relations.add(relation);
    }

    public void refreshAllPaths() {
        for (Relation r : relations) {
            findPath(r);
        }
    }

    
    /** 
     * find the path for relation with aStar
     * @param relation
     */
    private void findPath(Relation relation) {
        PathNode current = null;
        try {
            current = aStar.findPath(relation);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<ScaledPoint> pathPoints = new ArrayList<>();

        while (current != null) {
            HashSet<PathNode> relations = relationMap.get(current.position);
            if (relations == null) relations = new HashSet<>();
            relations.add(current);

            pathPoints.add(current.position);
            /*
            if (current.previous != null) {
                if (current.direction != current.previous.direction) {
                    pathPoints.add(current.position);
                }
            }

             */
            current = current.previous;
        }

        Collections.reverse(pathPoints);

        relation.setPath(pathPoints); //TODO: Något är fel, pathpoints innehåller två av samma punkt (sista)
    }

    
    /** 
     * Checks if two lines arrowtypes are the same and can merge, given that they are going to the same position
     * @param relation
     * @param position
     * @return boolean
     */
    boolean canMergeLines(Relation relation, ScaledPoint position) {
        if (!relationMap.containsKey(position)) return true;

        for (PathNode n : relationMap.get(position)) {
            if (n.relation.getTo() == relation.getTo() && n.relation.getArrowType() == relation.getArrowType()) {
                return true;
            }
        }
        return false;
    }

    
    /** 
     * Gets the list of relations
     * @return List<Relation>
     */
    public List<Relation> getRelations() {
        return new ArrayList<>(relations);
    }

    
    /** 
     * Remove a relation from the hashset
     * @param relation
     */
    public void remove(Relation relation) {
        relations.remove(relation);
    }
}
