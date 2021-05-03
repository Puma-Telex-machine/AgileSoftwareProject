package model.relations;

import model.boxes.Box;
import model.boxes.BoxType;
import model.facades.BoxFacade;
import model.point.ScaledPoint;

import java.util.ArrayList;
import java.util.List;

public class Relation {
    BoxFacade to;
    BoxFacade from;
    ArrowType arrowType;

    public Relation(BoxFacade from, BoxFacade to, ArrowType arrowType) {
        this.from = from;
        this.to = to;
        this.arrowType = arrowType;
    }

    public BoxFacade getTo() {
        return to;
    }

    public ScaledPoint getToPosition() {
        return to.getPosition();
    }

    public BoxFacade getFrom() {
        return from;
    }

    public ScaledPoint getFromPosition() {
        return from.getPosition();
    }

    public ArrowType getArrowType() {
        return arrowType;
    }

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
            return abstractClassRelations(to.getType());
        }
        if (from.getType() == BoxType.ENUM) {
            return enumRelations(to.getType());
        }
        return null;
    }

    private List<ArrowType> classRelations(BoxType to) {
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

    private List<ArrowType> interfaceRelations(BoxType to) {
        List<ArrowType> types = new ArrayList<>();
        if (to == BoxType.INTERFACE) {
            types.add(ArrowType.EXTENDS);
            return types;
        }
        return types;
    }

    private List<ArrowType> abstractClassRelations(BoxType to) {
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
   