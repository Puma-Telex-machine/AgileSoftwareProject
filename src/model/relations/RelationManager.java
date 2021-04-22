package model.relations;

import model.boxes.Box;
import model.boxes.BoxType;

import java.util.ArrayList;
import java.util.List;

public class RelationManager {

    public List<ArrowType> getPossibleRelations(Box from, Box to) {
        if (from.getType() == BoxType.BOX) {
            //TODO: fix
        }
        if (from.getType() == BoxType.CLASS) {
            return classRelations(to.getType());
        }
        if (from.getType() == BoxType.INTERFACE) {
            return interfaceRelations(to.getType());
        }
        if (from.getType() == BoxType.ABSTRACTCLASS) {
            return abstractclassRelations(to.getType());
        }
        if (from.getType() == BoxType.ENUM) {
            return enumRelations(to.getType());
        }
        return null;
    }

    private List<ArrowType> classRelations (BoxType to) {
        List<ArrowType> types = new ArrayList<>();
        if (to == BoxType.CLASS) {
            types.add(ArrowType.EXTENDS);
            return types;
        }
        if (to == BoxType.INTERFACE) {
            types.add(ArrowType.IMPLEMENTS);
            return types;
        }
        if (to == BoxType.ABSTRACTCLASS) {
            types.add(ArrowType.EXTENDS);
            return types;
        }
        return types;
    }

    private List<ArrowType> interfaceRelations (BoxType to) {
        List<ArrowType> types = new ArrayList<>();
        if (to == BoxType.INTERFACE) {
            types.add(ArrowType.EXTENDS);
            return types;
        }
        return types;
    }

    private List<ArrowType> abstractclassRelations(BoxType to) {
        List<ArrowType> types = new ArrayList<>();
        if (to == BoxType.INTERFACE) {
            types.add(ArrowType.IMPLEMENTS);
            return types;
        }
        if (to == BoxType.ABSTRACTCLASS) {
            types.add(ArrowType.IMPLEMENTS);
            return types;
        }
        if (to == BoxType.CLASS) {
            types.add(ArrowType.IMPLEMENTS);
            return types;
        }
        return types;
    }

    private List<ArrowType> enumRelations(BoxType to) {
        List<ArrowType> types = new ArrayList<>();
        if (to == BoxType.INTERFACE) {
            types.add(ArrowType.IMPLEMENTS);
            return types;
        }
        return types;
    }
}
