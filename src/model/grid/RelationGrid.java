package model.grid;

import model.point.ScaledPoint;
import model.relations.Relation;

import java.util.ArrayList;
import java.util.TreeMap;

public class RelationGrid {

    TreeMap<ScaledPoint, ArrayList<Relation>> relationMap = new TreeMap<>();

    boolean canMergeLines(Relation relation, ScaledPoint position) {
        if (!relationMap.containsKey(position)) return true;

        for (Relation n : relationMap.get(position)) {
            if (n.getTo() == relation.getTo() && n.getArrowType() == relation.getArrowType()) {
                return true;
            }
        }
        return false;
    }

    public void add(ArrayList<ScaledPoint> path, Relation relation) {
        for (ScaledPoint p : path) {
            ArrayList<Relation> relations = relationMap.get(p);
            relations.add(relation);
        }
    }
}
