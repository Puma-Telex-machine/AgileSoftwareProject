package model.boxes;

import frontend.Observers.UiObserver;
import model.facades.AttributeFacade;
import model.facades.BoxFacade;
import model.facades.MethodFacade;
import model.grid.IDiagram;
import model.point.Scale;
import model.point.ScaledPoint;

import java.util.*;

/**
 * A generalized class representing a UML object, specifically classes/interfaces/enums.
 * Originally created by Emil Holmsten,
 * Expanded by Filip Hanberg.
 */
public class Box implements BoxFacade, UiObserver {
    private static final int SYMBOLS_PER_WIDTH_UNIT = 10;

    private String name;
    private final BoxType type;
    private final List<MethodFacade> methods = new ArrayList<>();
    private final List<AttributeFacade> attributes = new ArrayList<>();
    private final Set<Modifier> modifiers = new HashSet<>();
    private Visibility visibility = Visibility.PUBLIC;
    private ScaledPoint position;
    private final IDiagram diagram;
    private final int MAGICNUMBERTEST = 2;

    public Box(IDiagram diagram, ScaledPoint position, BoxType type) {
        this.name = switch (type) {
            case CLASS -> "Class";
            case ABSTRACT_CLASS -> "Abstract Class";
            case INTERFACE -> "Interface";
            default -> "Box";
        };
        this.position = position;
        this.type = type;
        this.diagram = diagram;
        diagram.add(this);
        update();
    }

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
        diagram.remove(this);
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

    @Override
    public void setPosition(ScaledPoint point) {
        position = point;
        update();
    }

    @Override
    public ScaledPoint getPosition() {
        return position;
    }

    @Override
    public ScaledPoint getWidthAndHeight() {
        return new ScaledPoint(Scale.Backend, getWidth(), getHeight());
    }

    private int getHeight() {
        return getMethods().size() + getAttributes().size() + MAGICNUMBERTEST;
    }

    private int getWidth() {
        ArrayList<String> names = new ArrayList<>();

        names.add(name);
        for (MethodFacade method : methods) {
            names.add(method.getName());
        }
        for (AttributeFacade attribute : attributes) {
            names.add(attribute.getName());
        }

        ArrayList<Integer> longest = new ArrayList<>();
        for (String n : names) {
            longest.add(n.length());
        }

        int maxLength = Collections.max(longest);

        return maxLength * SYMBOLS_PER_WIDTH_UNIT;
    }


    ArrayList<UiObserver> observers = new ArrayList<>();

    @Override
    public void subscribe(UiObserver observer) {
        observers.add(observer);
    }

    @Override
    public void update() {
        diagram.update(this);
        for (UiObserver o : observers) {
            o.update();
        }
    }
}
