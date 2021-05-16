package model.diagram;

import model.boxes.Box;
import model.relations.Relation;

public interface DiagramMediator {
    void updateBox(Box box);
    void removeBox(Box box);
    void updateRelation(Relation relation);
    void removeRelation(Relation relation);
}
