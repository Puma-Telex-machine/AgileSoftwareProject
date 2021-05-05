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
            case ABSTRACTCLASS:
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
            case ABSTRACTCLASS:
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
            case ABSTRACTCLASS:
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
        switch(to){
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
        // Metoden boxRelations behövs förmodligen inte.
    private static List<ArrowType> boxRelations(BoxType to){
        List<ArrowType> types = new Arraylist<>();
        types.add(ArrowType.DEFAULT);
        return types;
    }    
    }
}
   