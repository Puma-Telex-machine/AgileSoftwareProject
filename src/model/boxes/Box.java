package model.boxes;

import global.Observer;
import global.Observers;
import global.TextWidthCalculator;
import global.point.Scale;
import global.point.ScaledPoint;
import model.diagram.DiagramMediator;
import model.facades.AttributeFacade;
import model.facades.MethodFacade;
import model.facades.UndoChain;

import java.util.*;

/**
 * A generalized class representing a UML object, specifically classes/interfaces/enums.
 * Originally created by Emil Holmsten,
 * Expanded by Filip Hanberg.
 */
public class Box implements BoxFacade, Observer {
    //different fontsize on name and other
    private static final int START_HEIGHT = 3;
    private static final int START_WIDTH = 4;
    private static final double ROWS_PER_HEIGHT_UNIT = 0.4999;

    private String name;
    private final BoxType type;
    private final List<Method> methods = new ArrayList<>();
    private final List<Attribute> attributes = new ArrayList<>();
    private final Set<Modifier> modifiers = new HashSet<>();
    private Visibility visibility = Visibility.PUBLIC;
    private ScaledPoint position;
    private DiagramMediator diagram;
    private UndoChain undoChain;

    private boolean isDeleted = false;


    public Box(DiagramMediator diagram, ScaledPoint position, BoxType type) {
        this.name = switch (type) {
            case CLASS -> "Class";
            case ABSTRACT_CLASS -> "Abstract Class";
            case INTERFACE -> "Interface";
            case ENUM -> "Enum";
            default -> "Box";
        };
        this.position = position;
        this.type = type;
        this.diagram = diagram;
        this.undoChain = diagram;
    }

    //region OBSERVABLE
    private final Observers observers = new Observers();

    @Override
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void update() {
        diagram.updateBox(this);
        observers.update();
    }

    private Boolean undoActive = true;

    @Override
    public void updateUndo() {
        if(undoActive)
            undoChain.updateUndo();
    }

    @Override
    public void stopUndo(){
        undoActive = false;
    }

    @Override
    public void resumeUndo() {
        undoActive = true;
    }
    //endregion


    /** 
     * sets the boxes name
     * @param name
     */
    @Override
    public void setName(String name) {
        this.name = name;
        update();
        updateUndo();
    }

    
    /** 
     * get the boxes name
     * @return String
     */
    @Override
    public String getName() {
        return name;
    }

    
    /** 
     * get the boxes type
     * @return BoxType
     */
    @Override
    public BoxType getType() {
        return type;
    }

    /**
     * deletes boxes
     */
    @Override
    public void deleteBox() {
        name = "THIS SHOULD NOT BE VISIBLE: BOX IS DELETED";
        this.isDeleted = true;
        diagram.removeBox(this);
    }

    
    /** 
     * adds a method in a list an returns it
     * @return MethodFacade
     */
    @Override
    public MethodFacade addMethod() {
        Method method = new Method(this);
        methods.add(method);
        method.subscribe(this);
        updateUndo();
        return method;
    }

    
    /** 
     * deletes a metod from a list
     * @param method
     */
    @Override
    public void deleteMethod(MethodFacade method) {
        methods.remove(method);
        update();
        updateUndo();
    }

    
    /** 
     * returns a list of methods
     * @return List<MethodFacade>
     */
    @Override
    public List<MethodFacade> getMethods() {
        return new ArrayList<>(methods);
    }

    
    /** 
     * adds an attribute in a list an returns it
     * @return AttributeFacade
     */
    @Override
    public AttributeFacade addAttribute() {
        Attribute attribute = new Attribute(this);
        attributes.add(attribute);
        attribute.subscribe(this);
        updateUndo();
        return attribute;
    }

    
    /** 
     * deletes an attribute from a list
     * @param attribute
     */
    @Override
    public void deleteAttribute(AttributeFacade attribute) {
        attributes.remove(attribute);
        update();
        updateUndo();
    }

    
    /** 
     * returns a list of attributes
     * @return List<AttributeFacade>
     */
    @Override
    public List<AttributeFacade> getAttributes() {
        return new ArrayList<>(attributes);
    }

    
    /** 
     * set the visibility of the box
     * @param visibility
     */
    @Override
    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
        update(); //Behövs denna?
        updateUndo();
    }

    
    /** 
     * get the visibility of the box
     * @return Visibility
     */
    @Override
    public Visibility getVisibility() {
        return visibility;
    }

    
    /** 
     * adds a modifier to a list of modifiers
     * @param modifier
     */
    @Override
    public void addModifier(Modifier modifier) {
        modifiers.add(modifier);
        update(); //Behövs denna?
        updateUndo();
    }

    
    /** 
     * remove a modifier from a list of modifiers
     * @param modifier
     */
    @Override
    public void removeModifier(Modifier modifier) {
        modifiers.remove(modifier);
        update();
        updateUndo();
    }

    
    /** 
     * returns a set of modifiers
     * @return Set<Modifier>
     */
    @Override
    public Set<Modifier> getModifiers() {
        return modifiers;
    }

  
    /** 
     * sets the boxes positions
     * @param point
     */
    @Override
    public void setPosition(ScaledPoint point) {
        position = point;
    }

    @Override
    public void setAndUpdatePosition(ScaledPoint point) {
        //updateUndo();
        position = point;
        update();
    }

    
    /** 
     * gets the positions of the box
     * @return ScaledPoint
     */
    @Override
    public ScaledPoint getPosition() {
        return position;
    }

  
    @Override
    //TODO: returns wrong height
    public ScaledPoint getWidthAndHeight() {
        int x = getWidth();
        int y = getHeight();
        return new ScaledPoint(Scale.Backend, x, y);
    }

    @Override
    public boolean isDeleted() {
        return isDeleted;
    }

    
    /** 
     * calculates the height of a box based on its metods and attributes
     * @return int
     */
    private int getHeight() {

        if((getMethods().size() + getAttributes().size()==0)) return START_HEIGHT;
        //+1 to round up.
        int height = (int) ((getMethods().size() + getAttributes().size()) * ROWS_PER_HEIGHT_UNIT) + START_HEIGHT +1;

        return height;
    }

    
    /** 
     * calculates the width ofa box based on the longest character name in it
     * @return int
     */
    private int getWidth() {

        ArrayList<String> names = new ArrayList<>();

        for (MethodFacade method : methods) {
            names.add(method.getString());
        }
        for (AttributeFacade attribute : attributes) {
            names.add(attribute.getString());
        }

        double longest=0;
        if(!names.isEmpty()){
            for (String n : names) {
                longest= Math.max(TextWidthCalculator.getInstance().computeTextWidthOther(n),longest);
            }
        }
        double a = TextWidthCalculator.getInstance().computeTextWidthName(name);
        longest= Math.max(a,longest);

        int i = new ScaledPoint(Scale.Frontend,longest,0).getX(Scale.Backend)+1;


        return Math.max(i,START_WIDTH);
    }

    public void setDiagram(DiagramMediator diagram) {
        this.diagram = diagram;
        this.undoChain = diagram;
    }
}
