package model.boxes;

//import model.relations.Relation;

import java.awt.*;
import java.util.*;

public class Diagram {

    ArrayList<Box> boxes = new ArrayList<>();
    //ArrayList<Relation> relations = new ArrayList<>();

    public void createBox(Point position, String name) {
        boxes.add(new Interface(position, name));
    }

    public Box getBox(int position){
        if(position < boxes.size() && position >= 0)
            return boxes.get(position);
        return null;
    }

   /*  public void createRelation(Box to, Box from, Relation.ArrowType arrowType) {
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
 */
}
