package model.diagram;

import model.boxes.BoxFacade;
import model.relations.RelationFacade;

public interface DiagramObserver {
    void addBox(BoxFacade box);
    void addRelation(RelationFacade relation);
}
