package model.boxes;

import global.Observer;
import global.Observers;
import global.point.Scale;
import global.point.ScaledPoint;
import model.diagram.DiagramMediator;
import model.facades.AttributeFacade;
import model.facades.MethodFacade;

import java.util.*;

/**
 * A generalized class representing a UML object, specifically classes/interfaces/enums.
 * Originally created by Emil Holmsten,
 * Expanded by Filip Hanberg.
 */
public class Box implements BoxFacade, Observer {
    //different fontsize on name and other
    private static final double SYMBOLS_PER_WIDTH_UNIT_NAME = 0.23;
    private static final double SYMBOLS_PER_WIDTH_UNIT_OTHER = 0.2;
    private static final int START_HEIGHT = 3;
    private static final int START_WIDTH = 5;
    private static final double ROWS_PER_HEIGHT_UNIT = 0.4999;

    private String name;
    private final BoxType type;
    private final List<MethodFacade> methods = new ArrayList<>();
    private final List<AttributeFacade> attributes = new ArrayList<>();
    private final Set<Modifier> modifiers = new HashSet<>();
    private Visibility visibility = Visibility.PUBLIC;
    private ScaledPoint position;
    private final DiagramMediator diagram;


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
    //endregion

    @Override
    public void setName(String name) {
        this.name = name;
        update();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BoxType getType() {
        return type;
    }

    @Override
    public void deleteBox() {
        name = "THIS SHOULD NOT BE VISIBLE: BOX IS DELETED";
        diagram.removeBox(this);
    }

    @Override
    public MethodFacade addMethod() {
        Method method = new Method();
        methods.add(method);
        method.subscribe(this);
        return method;
    }

    @Override
    public void deleteMethod(MethodFacade method) {
        methods.remove(method);
        update();
    }

    @Override
    public List<MethodFacade> getMethods() {
        return new ArrayList<>(methods);
    }

    @Override
    public AttributeFacade addAttribute() {
        Attribute attribute = new Attribute();
        attributes.add(attribute);
        attribute.subscribe(this);
        return attribute;
    }

    @Override
    public void deleteAttribute(AttributeFacade attribute) {
        attributes.remove(attribute);
        update();
    }

    @Override
    public List<AttributeFacade> getAttributes() {
        return new ArrayList<>(attributes);
    }

    @Override
    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
        update(); //Behövs denna?
    }

    @Override
    public Visibility getVisibility() {
        return visibility;
    }

    @Override
    public void addModifier(Modifier modifier) {
        modifiers.add(modifier);
        update(); //Behövs denna?
    }

    @Override
    public void removeModifier(Modifier modifier) {
        // TODO: deleteModifier? (consistency)
        modifiers.remove(modifier);
        update();
    }

    @Override
    public Set<Modifier> getModifiers() {
        return modifiers;
    }

    public void setPosition(ScaledPoint point) {
        position = point;
    }

    @Override
    public void setAndUpdatePosition(ScaledPoint point) {
        position = point;
        update();
    }

    @Override
    public ScaledPoint getPosition() {
        return position;
    }

    @Override
    //todo returns wrong height
    public ScaledPoint getWidthAndHeight() {
        return new ScaledPoint(Scale.Backend, getWidth(), getHeight());
    }

    private int getHeight() {

        if((getMethods().size() + getAttributes().size()==0)) return START_HEIGHT;
        //+1 to round up.
        int height = (int) ((getMethods().size() + getAttributes().size()) * ROWS_PER_HEIGHT_UNIT) + START_HEIGHT +1;

        return height;
    }

    private int getWidth() {

        //Todo width depends on characters maybe use textutil in boxController?
        // or should boxcontroller set width of the texts?
        
        ArrayList<String> names = new ArrayList<>();

        for (MethodFacade method : methods) {
            names.add(method.getString());
        }
        for (AttributeFacade attribute : attributes) {
            names.add(attribute.getString());
        }

        int maxLength = 0;
        if(!names.isEmpty()){
            ArrayList<Integer> longest = new ArrayList<>();
            for (String n : names) {
                longest.add(n.length());
            }

            maxLength = Collections.max(longest);
        }

        int boxLength = 0;
        if (maxLength < name.length()) {
            boxLength = Math.max((int) (name.length() * SYMBOLS_PER_WIDTH_UNIT_NAME) + 1, START_WIDTH);
        } else {
            boxLength = Math.max((int) (maxLength * SYMBOLS_PER_WIDTH_UNIT_OTHER) + 1, START_WIDTH);
        }

        if (Math.floorMod(boxLength, 2) == 0) {
            boxLength++;
        }

        return boxLength;
    }
}
