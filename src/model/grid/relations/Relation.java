package model.grid.relations;

import model.grid.boxes.Box;

public class Relation {
    Box to;
    Box from;
    ArrowType arrowType;
   
    Relation (Box from, Box to, ArrowType arrowType) {
        this.from = from;
        this.to = to;
        this.arrowType = arrowType;
    }
}
   