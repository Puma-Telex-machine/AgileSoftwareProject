package model.facades;

import model.point.ScaledPoint;
import model.relations.ArrowType;

import java.util.ArrayList;

public interface RelationFacade {
    ArrayList<ScaledPoint> getPath();
    ArrowType getArrowType();
    void changeRelation(ArrowType type);
    void subscribe(RelationObserver observer);
    BoxFacade getFrom();
    BoxFacade getTo();
}
