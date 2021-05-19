package model.relations;

import global.Observable;
import global.point.ScaledPoint;

import java.util.ArrayList;

public interface RelationFacade extends Observable<RelationObserver> {
    ArrayList<ScaledPoint> getPath();
    ArrowType getArrowType();
    void remove();
    void changeRelationType(ArrowType type);
}
