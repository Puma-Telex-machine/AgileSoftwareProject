package model.diagram;

import model.boxes.BoxFacade;
import model.relations.RelationFacade;

public interface ModelObserver {
    void addBox(BoxFacade box);
    void addRelation(RelationFacade relation);
    void clearCanvas();
}
