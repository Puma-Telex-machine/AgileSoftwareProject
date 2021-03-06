package model.relations;

import global.point.Scale;
import model.boxes.Box;
import model.boxes.BoxType;
import model.boxes.BoxFacade;
import model.diagram.DiagramMediator;
import global.point.ScaledPoint;

import java.util.ArrayList;
import java.util.List;

public class Relation implements RelationFacade {
    private DiagramMediator diagram;

    private final BoxFacade to;
    private boolean onLeftSideOfTo = false;
    private boolean onTopSideOfTo = false;
    private ScaledPoint offsetTo;
    private ScaledPoint offsetToInput;

    private final BoxFacade from;
    private boolean onLeftSideOfFrom = false;
    private boolean onTopSideOfFrom = false;
    private ScaledPoint offsetFrom;
    private ScaledPoint offsetFromInput;
    private String nrFrom,nrTo;

    private ArrowType arrowType;
    private ArrayList<ScaledPoint> path;

    private boolean isDeleted;

    public Relation(DiagramMediator diagram, BoxFacade from, ScaledPoint offsetFrom, BoxFacade to, ScaledPoint offsetTo, ArrowType arrowType) {
        this.diagram = diagram;
        this.from = from;
        this.offsetFrom = calculateOffset(offsetFrom, from, true);
        this.offsetFromInput = offsetFrom;
        this.to = to;
        this.offsetTo = calculateOffset(offsetTo, to, false);
        this.offsetToInput = offsetTo;
        this.arrowType = arrowType;
        this.nrTo="";
        this.nrFrom="";
    }

    /**
     * Calculates the offset independent of target width and height by removing the width and height (to be added again later).
     * Also sets what side the box the arrow originates from.
     * @param offset the offset from the initial position
     * @param target the box from where the offset is
     * @return the recalculated offset
     */
    private ScaledPoint calculateOffset(ScaledPoint offset, BoxFacade target, boolean isFrom) {
        ScaledPoint calculatedOffset = new ScaledPoint(offset);
        if (offset.getX(Scale.Backend) == 0) {
            if (isFrom) {
                onLeftSideOfFrom = true;
            } else {
                onLeftSideOfTo = true;
            }
        } else {
            calculatedOffset = calculatedOffset.move(Scale.Backend, -target.getWidthAndHeight().getX(Scale.Backend), 0);
        }
        if (offset.getY(Scale.Backend) == 0) {
            if (isFrom) {
                onTopSideOfFrom = true;
            } else {
                onTopSideOfTo = true;
            }
        } else {
            calculatedOffset = calculatedOffset.move(Scale.Backend, 0, -target.getWidthAndHeight().getY(Scale.Backend));
        }
        return calculatedOffset;
    }

    //region OBSERVABLE
    private final ArrayList<RelationObserver> observers = new ArrayList<>();

    @Override
    public void subscribe(RelationObserver observer) {
        observers.add(observer);
    }

    private void updateObserver() {
        for (RelationObserver o : observers) {
            o.update(this);
        }
    }
    //endregion

    public ScaledPoint getStartPosition() {
        ScaledPoint startPosition = from.getPosition().move(offsetFrom);
        if (!onLeftSideOfFrom) {
            startPosition = startPosition.move(Scale.Backend, from.getWidthAndHeight().getX(Scale.Backend), 0);
            if (startPosition.getX(Scale.Backend) <= from.getPosition().getX(Scale.Backend) + 1) { // +1 is the first point instead of the edge of the box
                startPosition = new ScaledPoint(Scale.Backend, from.getPosition().getX(Scale.Backend) + 1, startPosition.getY(Scale.Backend));
            }
        }
        if (!onTopSideOfFrom) {
            startPosition = startPosition.move(Scale.Backend, 0, from.getWidthAndHeight().getY(Scale.Backend));
            if (startPosition.getY(Scale.Backend) < from.getPosition().getY(Scale.Backend)) {
                startPosition = new ScaledPoint(Scale.Backend, startPosition.getX(Scale.Backend), from.getPosition().getY(Scale.Backend) + 1);
            }
        }
        return startPosition;
    }

    public ScaledPoint getEndPosition() {
        ScaledPoint endPosition = to.getPosition().move(offsetTo);
        if (!onLeftSideOfTo) {
            endPosition = endPosition.move(Scale.Backend, to.getWidthAndHeight().getX(Scale.Backend), 0);
            if (endPosition.getX(Scale.Backend) <= to.getPosition().getX(Scale.Backend) + 1) { // +1 is the first point instead of the edge of the box
                endPosition = new ScaledPoint(Scale.Backend, to.getPosition().getX(Scale.Backend) + 1, endPosition.getY(Scale.Backend));
            }
        }
        if (!onTopSideOfTo) {
            endPosition = endPosition.move(Scale.Backend, 0, to.getWidthAndHeight().getY(Scale.Backend));
            if (endPosition.getY(Scale.Backend) < to.getPosition().getY(Scale.Backend)) {
                endPosition = new ScaledPoint(Scale.Backend, endPosition.getX(Scale.Backend), to.getPosition().getY(Scale.Backend) + 1);
            }
        }
        return endPosition;
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

    @Override
    public void remove() {
        diagram.removeRelation(this);
        this.isDeleted = true;
    }
    
    /** 
     * Get the BoxFacade to
     * @return BoxFacade
     */
    public BoxFacade getTo() {
        return to;
    }

    @Override
    public ScaledPoint getOffsetTo() {
        return offsetTo;
    }

    public ScaledPoint getOffsetToInput(){return offsetToInput;}

    public void removeIfDisconnected() {
        if (to.isDeleted() || from.isDeleted()) {
            this.isDeleted = true;
            diagram.removeRelation(this);
            updateObserver();
        }
    }

    
    
    @Override
    public boolean isDeleted() {
        return isDeleted;
    }

    
   /** 
     * TODO: shouldn't ot be setsubscriber or something
     * @param observer
     */
    @Override
    public String getNrFrom() {
        return nrFrom;
    }

    @Override
    public String getNrTo() {
        return nrTo;
    }

    @Override
    public void setNrTo(String nrTo) {
        this.nrTo=nrTo;
    }

    @Override
    public void setNrFrom(String nrFrom) {
        this.nrFrom=nrFrom;
    }

	/**
	* Returns the BoxFacade "from"
	* @return the BoxFacade.
	*/
    public BoxFacade getFrom() {
        return from;
    }

    public ScaledPoint getOffsetFrom() {return offsetFrom;}

    public ScaledPoint getOffsetFromInput() {return offsetFromInput;}

/** 
     * change the relationtype of an arrow
     * @param type
     */
    @Override
    public void changeRelationType(ArrowType type) {
        this.arrowType = type;
        diagram.updateRelation(this);
        updateObserver();
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
        // TODO: kolla vidare p?? det h??r     types.add(ArrowType.EXTENDS);
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

    public void setDiagram(DiagramMediator diagram) {
        this.diagram = diagram;
    }
}
   