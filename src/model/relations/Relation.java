package model.relations;

import model.boxes.Box;

public class Relation {
    Box to;
    Box from;
    ArrowType arrowType;

    public Relation(Box from, Box to, ArrowType arrowType) {
        this.from = from;
        this.to = to;
        this.arrowType = arrowType;
    }

    public Box getTo() {
        return to;
    }

    public Box getFrom() {
        return from;
    }

    public ArrowType getArrowType() {
        return arrowType;
    }
}
   