package model.boxes;

import model.facades.*;
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
    private List<Method> methods = new ArrayList<>();
    private List<Attribute> attributes = new ArrayList<>();
    private Set<Modifier> modifiers = new HashSet<>();
    private Visibility visibility = Visibility.PUBLIC;
    private ScaledPoint position;
    private final IDiagram observer;

    Box(IDiagram observer, String name, ScaledPoint position, BoxType type) {
        this.name = name;
        this.position = position;
        this.type = type;
        this.observer = observer;
    }

    @Override
    public void setName(String newName) {
        name = newName;
        observer.update(this);
    }

    @Override
    public void deleteBox() {
        name = "THIS SHOULD NOT BE VISIBLE: BOX DELETED";
        observer.remove(this);
    }

    @Override
    public void editMethod(MethodData methodData) {
        boolean exists = false;
        for (Method method: methods) {
            if(methodData.methodName.equals(method.getName())) {
                exists = true;
                //method.SetName(methodData.methodName); todo: identify methods
                method.setVisibility(methodData.visibility);
                method.SetArguments(methodData);
                break;
            }
        }
        if(!exists){
            methods.add(new Method(methodData));
        }
        observer.update(this);
    }

    @Override
    public void deleteMethod(String methodName) {
        int counter = 0;
        for (Method method: methods) {
            if(methodName.equals(method.getName())) {
                methods.remove(counter);
                break;
            }
            counter++;
        }
        observer.update(this);
    }

    @Override
    public void addVariable() {
        attributes.add(new Attribute());
        observer.update(this);
    }

    @Override
    public void deleteVariable(String variableName) {
        // TODO: deleteAttribute?
        int counter = 0;
        for (Attribute attribute: attributes) {
            if(variableName.equals(attribute.getName())) {
                attributes.remove(counter);
                break;
            }
            counter++;
        }
        observer.update(this);
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
    public List<MethodFacade> getMethods() { //TODO: Method interface return?
        return new ArrayList<>(methods);
    }

    @Override
    public List<AttributeFacade> getAttributes() {
        return new ArrayList<>(attributes);
    }

    @Override
    public void addModifier(Modifier modifier) {
        modifiers.add(modifier);
        observer.update(this); //Behövs denna?
    }

    @Override
    public void removeModifier(Modifier modifier) {
        // TODO: deleteModifier? (consistency)
        modifiers.remove(modifier);
        observer.update(this);
    }

    @Override
    public Set<Modifier> getModifiers() {
        return modifiers;
    }

    @Override
    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
        observer.update(this); //Behövs denna?
    }

    @Override
    public Visibility getVisibility() {
        return visibility;
    }

    @Override
    public void setPosition(ScaledPoint newPosition) {
        position = newPosition;
        observer.update(this);
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
        for (Method method : methods) {
            names.add(method.getName());
        }
        for (Method method : methods) {
            names.add(method.getName());
        }

        ArrayList<Integer> longest = new ArrayList<>();
        for (String n : names) {
            longest.add(n.length());
        }

        int maxLength = Collections.max(longest);

        return maxLength * SYMBOLS_PER_WIDTH_UNIT;
    }
}  
