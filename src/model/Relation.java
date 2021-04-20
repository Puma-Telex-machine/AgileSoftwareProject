package model;

public class Relation {
    Box to;
    Box from; // Alt box innehåller en relation (istället för from)

    ArrowType arrowType;
    /*
    Relation(Box to, ....) {

    }
     */

    enum ArrowType {
        EXTENDS,
        IMPLEMENTS
    }
}
