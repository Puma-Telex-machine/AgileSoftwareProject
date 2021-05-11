package model.facades;

import model.relations.RelationFacade;

public interface Observer {
    void addBox(BoxFacade box);
    void addRelation(RelationFacade relation);
}
