package model.boxes;

import model.MethodData;
import model.VariableData;
import model.facades.BoxFacade;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * A generalized class representing a UML object, specifically classes/interfaces/enums.
 * Originally created by Emil Holmsten,
 * Expanded by Filip Hanberg.
 */
public class Box {

    private String name;
    private List<Method> methods;
    private List<Attribute> attributes;
    private Set<Modifier> modifiers;
    private Visibility visibility;
    private Point position;

    public Box(String name, Point position) {
        this.name = name;
        this.position = position;
        methods = new ArrayList<>();
        attributes = new ArrayList<>();
        modifiers = new HashSet<>();
    }

    public BoxType getType(){
        return BoxType.BOX;
    }

    public void setName(String newName){
        name = newName;
    }

    public void setPosition(Point newPosition){
        position = newPosition;
    }

    public Method getMethod(int position){
        if(position < methods.size() && position >= 0)
            return methods.get(position);
        return null;
    }

    public Attribute getAttribute(int position){
        if(position < attributes.size() && position >= 0)
            return attributes.get(position);
        return null;
    }

    public void setModifiers(Set<Modifier> modifiers){
        this.modifiers = modifiers;
    }

    public void addModifier(Modifier modifier){
        modifiers.add(modifier);
    }

    public void removeModifier(Modifier modifier){
        modifiers.remove(modifier);
    }

    public void setVisibility(Visibility visibility){
        this.visibility = visibility;
    }

    public String getName() {
        return name;
    }

    public void editMethod(MethodData methodData) {
        boolean exists = false;
        for (Method method: methods) {
            if(methodData.methodName == method.GetName()){
                exists = true;
                //method.SetName(methodData.methodName); todo: identify methods
                method.SetVisibility(methodData.visibility);
                method.SetArguments(methodData);
                break;
            }
        }
        if(!exists){
            methods.add(new Method(methodData));
        }
    }

    public void editVariable(VariableData variableData) {
        boolean exists = false;
        for (Attribute attribute: attributes) {
            if(variableData.name == attribute.GetName()){
                exists = true;
                //attribute.SetName(variableData.name); todo: identify attributes
                attribute.SetVisibility(variableData.visibility);
                break;
            }
        }
        if(!exists){
            attributes.add(new Attribute(variableData));
        }
    }

    public void deleteMethod(String methodName) {
        int counter = 0;
        for (Method method: methods) {
            if(methodName == method.GetName()) {
                methods.remove(counter);
                break;
            }
            counter++;
        }
    }

    public void deleteVariable(String variableName) {
        int counter = 0;
        for (Attribute attribute: attributes) {
            if(variableName == attribute.GetName()) {
                attributes.remove(counter);
                break;
            }
            counter++;
        }
    }

    public Point getPosition(){return position; }

    public List<Method> getMethods(){return methods;}

    public List<Attribute> getAttributes(){return attributes;}

    public Set<Modifier> getModifiers(){return modifiers;}

    public Visibility GetVisibility(){
        return visibility;
    }

    public int getHeight() {
        return 0;
    }
}
