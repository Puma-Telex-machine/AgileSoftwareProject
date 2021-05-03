package model.facades;

import model.point.ScaledPoint;
import model.relations.Relation;

import java.util.ArrayList;

public interface RelationFacade {
    Relation getRelation();
    ArrayList<ScaledPoint> getPathPoints();
}
