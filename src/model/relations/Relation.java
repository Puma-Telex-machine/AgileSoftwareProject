package model.relations;

import model.boxes.Box;
import model.boxes.BoxType;
import model.boxes.BoxFacade;
import model.diagram.IDiagram;
import global.point.ScaledPoint;

import java.util.ArrayList;
import java.util.List;

public class Relation implements RelationFacade {
    private final IDiagram diagram;
    private final BoxFacade to;
    private final BoxFacade from;
    private ArrowType arrowType;
    private ArrayList<ScaledPoint> path;

    public Relation(IDiagram diagram, BoxFacade from, BoxFacade to, ArrowType arrowType) {
        this.diagram = diagram;
        this.from = from;
        this.to = to;
        this.arrowType = arrowType;
    }

    //region OBSERVABLE
    private final ArrayList<RelationObserver> observers = new ArrayList<>();

    @Override
    public void subscribe(RelationObserver observer) {
        observers.add(observer);
    }

    private void updateObserver(){
        for (RelationObserver o : observers) {
            o.update(this);
        }
    }
    //endregion

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
    public void remove() {
        diagram.removeRelation(this);
    }

    public BoxFacade getTo() {
        return to;
    }

    public BoxFacade getFrom() {
        return from;
    }

    @Override
    public void changeRelationType(ArrowType type) {
        this.arrowType = type;
        updateObserver();
    }

    public void setPath(ArrayList<ScaledPoint> pathPoints) {
        this.path = pathPoints;
        updateObserver();
    }

    public static List<ArrowType> getPossibleRelations(Box from, Box to) {
        BoxType toType = to.getType();
        switch (from.getType()){
            case BOX:
                ArrayList<ArrowType> defaultArrow = new ArrayList<ArrowType>();
                defaultArrow.add(ArrowType.DEFAULT);
                return defaultArrow;
            case CLASS:
                return classRelations(toType);
            case INTERFACE:
                return interfaceRelations(toType);
            case ABSTRACT_CLASS:
                return abstractclassRelations(toType);
            case ENUM:
                return enumRelations(toType);
            default:
                return null;
        }
    }

    private static List<ArrowType> classRelations(BoxType to) {
        List<ArrowType> types = new ArrayList<>();
        switch(to){
            case BOX:
            ArrayList<ArrowType> defaultArrow = new ArrayList<ArrowType>();
            defaultArrow.add(ArrowType.DEFAULT);
            return defaultArrow;
            case CLASS:
                types.add(ArrowType.INHERITANCE);
                types.add(ArrowType.AGGREGATION);
                return types;
            case INTERFACE:
                types.add(ArrowType.IMPLEMENTATION);
                return types;
            case ABSTRACT_CLASS:
                types.add(ArrowType.INHERITANCE);
                return types;
            default:
                return null;
        }
    }

    private static List<ArrowType> interfaceRelations(BoxType to) {
        List<ArrowType> types = new ArrayList<>();
        switch(to){
            case BOX:
            ArrayList<ArrowType> defaultArrow = new ArrayList<ArrowType>();
            defaultArrow.add(ArrowType.DEFAULT);
            return defaultArrow;
            case INTERFACE:
                types.add(ArrowType.INHERITANCE);
        // TODO: kolla vidare på det här     types.add(ArrowType.EXTENDS);
                return types;
            default:
                return null;
        }
    }

    private static List<ArrowType> abstractclassRelations(BoxType to) {
        List<ArrowType> types = new ArrayList<>();
        switch(to){
            case BOX:
            ArrayList<ArrowType> defaultArrow = new ArrayList<ArrowType>();
            defaultArrow.add(ArrowType.DEFAULT);
            return defaultArrow;
            case INTERFACE:
                types.add(ArrowType.IMPLEMENTATION);
                return types;
            case ABSTRACT_CLASS:
                types.add(ArrowType.IMPLEMENTATION);
                return types;
            case CLASS:
                types.add(ArrowType.IMPLEMENTATION);
                return types;
            default:
                return null;
        }
    }

    private static List<ArrowType> enumRelations(BoxType to) {
        List<ArrowType> types = new ArrayList<>();
        switch (to) {
            case BOX:
                ArrayList<ArrowType> defaultArrow = new ArrayList<ArrowType>();
                defaultArrow.add(ArrowType.DEFAULT);
                return defaultArrow;
            case INTERFACE:
                types.add(ArrowType.IMPLEMENTATION);
                return types;
            default:
                return null;
        }
    }
}
   