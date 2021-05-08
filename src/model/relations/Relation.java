package model.relations;

import model.boxes.Box;

import java.util.*;

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
   