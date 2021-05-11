package model.diagram;

import global.Observable;
import global.point.ScaledPoint;
import model.boxes.BoxType;
import model.boxes.BoxFacade;
import model.relations.ArrowType;

public interface DiagramFacade extends Observable<DiagramObserver> {
    void createBox(ScaledPoint position, BoxType boxType);
    void createRelation(BoxFacade from, ScaledPoint offsetFrom, BoxFacade to, ScaledPoint offsetTo, ArrowType arrowType);
}
