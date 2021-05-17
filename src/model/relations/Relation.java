package model.relations;

import model.boxes.Box;
import model.boxes.BoxType;
import model.facades.BoxFacade;
import model.facades.RelationFacade;
import model.facades.RelationObserver;
import model.point.ScaledPoint;

import java.util.ArrayList;
import java.util.List;

public class Relation implements RelationFacade {
    BoxFacade to;
    BoxFacade from;
    ArrowType arrowType;
    ArrayList<ScaledPoint> path;
    RelationObserver observer=null;

    public Relation(BoxFacade from, BoxFacade to, ArrowType arrowType) {
        this.from = from;
        this.to = to;
        this.arrowType = arrowType;
    }

    
    /** 
     * Gets the position from the box to
     * @return ScaledPoint
     */
    public ScaledPoint getToPosition() {
        return to.getPosition();
    }

    
    /** 
     * Gets the position from the box from
     * @return ScaledPoint
     */
    public ScaledPoint getFromPosition() {
        return from.getPosition();
    }

    
    /** 
     * Returns an arraylist of the paths
     * @return ArrayList<ScaledPoint>
     */
    @Override
    public ArrayList<ScaledPoint> getPath() {
        return path;
    }

    
    /** 
     * Gets the arrowtype
     * @return ArrowType
     */
    @Override
    public ArrowType getArrowType() {
        return arrowType;
    }

    
    /** 
     * Get the BoxFacade to
     * @return BoxFacade
     */
    public BoxFacade getTo() {
        return to;
    }


    
    /** 
     * Get the BoxFacade from
     * @return BoxFacade
     */
    public BoxFacade getFrom() {
        return from;
    }
    
    /** 
     * change the relationtype of an arrow
     * @param type
     */
    @Override
    public void changeRelation(ArrowType type) {
        this.arrowType = type;
        updateObserver();
    }

    
   /** 
     * TODO: shouldn't ot be setsubscriber or something
     * @param observer
     */
    @Override
    public void subscribe(RelationObserver observer) {
        this.observer=observer;
    }
    private void updateObserver(){
        if(observer!=null) observer.updateRelation(this);
    }

    
    /** 
     * Set the paths for the relations
     * @param pathPoints
     */
    public void setPath(ArrayList<ScaledPoint> pathPoints) {
        this.path = pathPoints;
        updateObserver();
    }

    
    /** 
     * Get the possible arrowtype depending on the two boxes types
     * @param from
     * @param to
     * @return List<ArrowType>
     */
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
    
    

    
    /** 
     * Returns a list of which possible arrowtypes depending on the to-box, where the from-box, is a classtype
     * @param to
     * @return List<ArrowType>
     */
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

    
    /** 
     * Returns a list of which possible arrowtypes depending on the to-box, where the from-box, is a interface
     * @param to
     * @return List<ArrowType>
     */
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

    
    /** 
     * Returns a list of which possible arrowtypes depending on the to-box, where the from-box, is a abstractclass
     * @param to
     * @return List<ArrowType>
     */
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

    
    /** 
     * Returns a list of which possible arrowtypes depending on the to-box, where the from-box, is a enum
     * @param to
     * @return List<ArrowType>
     */
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
        
        // Metoden boxRelations behövs förmodligen inte.
    private static List<ArrowType> boxRelations(BoxType to){
        List<ArrowType> types = new ArrayList<>();
        types.add(ArrowType.DEFAULT);
        return types;
        }
}
   