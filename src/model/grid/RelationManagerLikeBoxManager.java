package model.grid;

import model.facades.RelationFacade;
import model.point.ScaledPoint;
import model.relations.Relation;

import java.util.ArrayList;

public class RelationManagerLikeBoxManager implements RelationFacade {

    Relation relation;
    ArrayList<ScaledPoint> pathPoints;

    public RelationManagerLikeBoxManager(Relation relation, ArrayList<ScaledPoint> pathPoints) {
        this.relation = relation;
        this.pathPoints = pathPoints;
    }

    public Relation getRelation() {
        return relation;
    }

    public ArrayList<ScaledPoint> getPathPoints() {
        return pathPoints;
    }
}
