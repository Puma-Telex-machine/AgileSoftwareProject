package model.boxes;

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
public class Box implements BoxFacade {
    private static final int SYMBOLS_PER_WIDTH_UNIT = 10;

    private String name;
    private final BoxType type;
    private final List<MethodFacade> methods = new ArrayList<>();
    private final List<AttributeFacade> attributes = new ArrayList<>();
    private final Set<Modifier> modifiers = new HashSet<>();
    private Visibility visibility = Visibility.PUBLIC;
    private ScaledPoint position;
    private final IDiagram observer;

    public Box(IDiagram observer, ScaledPoint position, BoxType type) {
        String defaultName;
        switch (type) {
            case CLASS -> defaultName = "Class";
            case ABSTRACT_CLASS -> defaultName =  "Abstract Class";
            case INTERFACE -> defaultName = "Interface";
            default -> defaultName = "Box";
        }
        this.name = defaultName;
        this.position = position;
        this.type = type;
        this.observer = observer;
        observer.set(this);
    }

    @Override
    public void setName(String name) {
        this.name = name;
        observer.set(this);
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
        observer.remove(this);
    }

    @Override
    public MethodFacade addMethod() {
        return new Method();
    }

    @Override
    public void deleteMethod(MethodFacade method) {
        methods.remove(method);
        observer.set(this);
    }

    @Override
    public List<MethodFacade> getMethods() {
        return new ArrayList<>(methods);
    }

    @Override
    public AttributeFacade addAttribute() {
        AttributeFacade attribute = new Attribute();
        attributes.add(attribute);
        observer.set(this);
        return attribute;
    }

    @Override
    public void deleteAttribute(AttributeFacade attribute) {
        attributes.remove(attribute);
        observer.set(this);
    }

    @Override
    public List<AttributeFacade> getAttributes() {
        return new ArrayList<>(attributes);
    }

    @Override
    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
        observer.set(this); //Behövs denna?
    }

    @Override
    public Visibility getVisibility() {
        return visibility;
    }

    @Override
    public void addModifier(Modifier modifier) {
        modifiers.add(modifier);
        observer.set(this); //Behövs denna?
    }

    @Override
    public void removeModifier(Modifier modifier) {
        // TODO: deleteModifier? (consistency)
        modifiers.remove(modifier);
        observer.set(this);
    }

    @Override
    public Set<Modifier> getModifiers() {
        return modifiers;
    }

    @Override
    public void setPosition(ScaledPoint point) {
        position = point;
        observer.set(this);
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
        return getMethods().size() + getAttributes().size();
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
}  
