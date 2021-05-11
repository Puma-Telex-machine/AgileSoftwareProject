package model.facades;

import model.relations.RelationFacade;

public interface RelationObserver {
    void updateRelation(RelationFacade relation);
}
