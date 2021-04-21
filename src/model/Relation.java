package model;

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

    public List<ArrowType> getPossibleRelations(Box from, Box to) {
        if (from.getType() == "class") {
            return classRelations(to.getType());
        }
        if (from.getType() == "interface") {
            return interfaceRelations(to.getType());
        }
        if (from.getType() == "abstractclass") {
            return abstractclassRelations(to.getType());
        }
        if (from.getType() == "enum") {
            return enumRelations(to.getType());
        }
        return null;
    }

    enum ArrowType {
        EXTENDS,
        IMPLEMENTS
    }
    private List<ArrowType> classRelations (String to) {
        List<ArrowType> types = new ArrayList<>();
        if (to == "class") {
            types.add(ArrowType.EXTENDS);
            return types;
        }
        if (to == "interface") {
            types.add(ArrowType.IMPLEMENTS);
            return types;
        }
        if (to == "abstractclass") {
            types.add(ArrowType.EXTENDS);
            return types;
        }
        return types;
    }

    private List<ArrowType> interfaceRelations (String to) {
        List<ArrowType> types = new ArrayList<>();
        if (to == "interface") {
            types.add(ArrowType.EXTENDS);
            return types;
        }
        return types;
    }
    
    private List<ArrowType> abstractclassRelations(String to) {
        List<ArrowType> types = new ArrayList<>();
        if (to == "interface") {
            types.add(ArrowType.IMPLEMENTS);
            return types;
        }
        if (to == "abstractclass") {
            types.add(ArrowType.IMPLEMENTS);
            return types;
        }
        if (to == "class") {
            types.add(ArrowType.IMPLEMENTS);
            return types;
        }
        return types;
    }
    
    private List<ArrowType> enumRelations(String to) {
        List<ArrowType> types = new ArrayList<>();
        if (to == "interface") {
            types.add(ArrowType.IMPLEMENTS);
            return types;
        }
        return types;
    }
}
   