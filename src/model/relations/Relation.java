package model.relations;

import model.boxes.Box;
import model.boxes.BoxType;
import model.facades.BoxFacade;
import model.facades.RelationFacade;
import model.point.ScaledPoint;

import java.util.ArrayList;
import java.util.List;

public class Relation implements RelationFacade {
    BoxFacade to;
    BoxFacade from;
    ArrowType arrowType;
    ArrayList<ScaledPoint> path;

    public Relation(BoxFacade from, BoxFacade to, ArrowType arrowType) {
        this.from = from;
        this.to = to;
        this.arrowType = arrowType;
    }

    public BoxFacade getTo() {
        return to;
    }

    public BoxFacade getFrom() {
        return from;
    }

    public ScaledPoint getToPosition() {
        return to.getPosition();
    }

    public ScaledPoint getFromPosition() {
        return from.getPosition();
    }

    @Override
    public ArrayList<ScaledPoint> getPath() {
        return path;
    }

    @Override
    public ArrowType getArrowType() {
        return arrowType;
    }

    @Override
    public void changeRelation(ArrowType type) {
        this.arrowType = type;
    }

    public void setPath(ArrayList<ScaledPoint> pathPoints) {
        this.path = pathPoints;
    }

    public static List<ArrowType> getPossibleRelations(Box from, Box to) {
        //TODO: Refactor to switch

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

    private static List<ArrowType> classRelations(BoxType to) {
        List<ArrowType> types = new ArrayList<>();
        if (to == BoxType.CLASS) {
            types.add(ArrowType.INHERITANCE);
            return types;
        }
        if (to == BoxType.INTERFACE) {
            types.add(ArrowType.IMPLEMENTATION);
            return types;
        }
        if (to == BoxType.ABSTRACTCLASS) {
            types.add(ArrowType.INHERITANCE);
            return types;
        }
        return types;
    }

    private static List<ArrowType> interfaceRelations(BoxType to) {
        List<ArrowType> types = new ArrayList<>();
        if (to == BoxType.INTERFACE) {
            types.add(ArrowType.INHERITANCE);
            return types;
        }
        return types;
    }

    private static List<ArrowType> abstractclassRelations(BoxType to) {
        List<ArrowType> types = new ArrayList<>();
        if (to == BoxType.INTERFACE) {
            types.add(ArrowType.IMPLEMENTATION);
            return types;
        }
        if (to == BoxType.ABSTRACTCLASS) {
            types.add(ArrowType.IMPLEMENTATION);
            return types;
        }
        if (to == BoxType.CLASS) {
            types.add(ArrowType.IMPLEMENTATION);
            return types;
        }
        return types;
    }

    private static List<ArrowType> enumRelations(BoxType to) {
        List<ArrowType> types = new ArrayList<>();
        if (to == BoxType.INTERFACE) {
            types.add(ArrowType.IMPLEMENTATION);
            return types;
        }
        return types;
    }
}
   