package model.relations;

import global.Observable;
import global.point.ScaledPoint;
import model.boxes.BoxFacade;

import java.util.ArrayList;

public interface RelationFacade extends Observable<RelationObserver> {
    ArrayList<ScaledPoint> getPath();
    ArrowType getArrowType();
    void remove();
    void changeRelationType(ArrowType type);
    BoxFacade getTo();
    BoxFacade getFrom();
    ScaledPoint getOffsetTo();
    ScaledPoint getOffsetFrom();
}
