package model;

import java.util.ArrayList;
import java.util.List;

public class Diagram {

    ArrayList<Box> boxes = new ArrayList<>();
    ArrayList<Relation> relations = new ArrayList<>();

    public void createBox() {

    }

    public void createRelation(Box to, Box from, Relation.ArrowType arrowType) {
        relations.add(new Relation(to, from, arrowType));
    }

    public List<Relation.ArrowType> getPossibleRelations(Box to, Box from) {
        return null;
    }

    public List<Relation.ArrowType> getPossibleRelations(Class to, Class from) {
        return null;
    }

    public List<Relation.ArrowType> getPossibleRelations(Interface to, Class from) {
        return null;
    }
}
