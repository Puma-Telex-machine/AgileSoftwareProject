package model.boxes;

import model.facades.MethodData;
//import model.facades.VariableData;
import model.facades.VariableData;
import model.point.ScaledPoint;

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
    private List<Method> methods = new ArrayList<>();
    private List<Attribute> attributes = new ArrayList<>();
    private Set<Modifier> modifiers = new HashSet<>();
    private Visibility visibility = Visibility.PUBLIC;
    private ScaledPoint position;

    public Box(String name, ScaledPoint position) {
        this.name = name;
        this.position = position;
    }

    public BoxType getType(){
        return BoxType.BOX;
    }

    public void setName(String newName){
        name = newName;
    }

    public void setPosition(ScaledPoint newPosition){
        position = newPosition;
    }

    public void setMethods(List<Method> newMethods){
        methods = newMethods;
    }

    public void setAttributes(List<Attribute> newAttributes){
        attributes = newAttributes;
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
            if(methodData.methodName == method.getName()){
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
            if(variableData.name == attribute.getName()){
                exists = true;
                //attribute.SetName(variableData.name); todo: identify attributes
                attribute.setVisibility(variableData.visibility);
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
            if(methodName == method.getName()) {
                methods.remove(counter);
                break;
            }
            counter++;
        }
    }

    public void deleteVariable(String variableName) {
        int counter = 0;
        for (Attribute attribute: attributes) {
            if(variableName == attribute.getName()) {
                attributes.remove(counter);
                break;
            }
            counter++;
        }
    }

    public ScaledPoint getPosition(){return position; }

    public List<Method> getMethods(){return methods;}

    public List<Attribute> getAttributes(){return attributes;}

    public Set<Modifier> getModifiers(){return modifiers;}

    public Visibility getVisibility(){
        return visibility;
    }

    public int getHeight() {
        return 0;
    }
}
