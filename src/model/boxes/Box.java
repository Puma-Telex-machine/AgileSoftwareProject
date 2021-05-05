package model.boxes;

import model.facades.MethodData;
import model.facades.VariableData;
import model.point.ScaledPoint;

import java.util.*;
import java.util.List;

/**
 * A generalized class representing a UML object, specifically classes/interfaces/enums.
 * Originally created by Emil Holmsten,
 * Expanded by Filip Hanberg.
 */
public class Box {

    private String name;
    private BoxType type;
    private List<Method> methods = new ArrayList<>();
    private List<Attribute> attributes = new ArrayList<>();
    private Set<Modifier> modifiers = new HashSet<>();
    private Visibility visibility = Visibility.PUBLIC;
    private ScaledPoint position;

    public Box(String name, ScaledPoint position, BoxType type) {
        this.name = name;
        this.position = position;
        this.type = type;
    }

    public void setName(String newName){
        name = newName;
    }

    public String getName() {
        return name;
    }

    public BoxType getType(){
        return type;
    }

    public void setType(BoxType boxtype){
        type = boxtype;
    }

    public void setMethods(List<Method> newMethods){
        methods = newMethods;
    }

    public Method getMethod(int position){
        if(position < methods.size() && position >= 0)
            return methods.get(position);
        return null;
    }

    public List<Method> getMethods(){
        return methods;}

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

    public void setAttributes(List<Attribute> newAttributes){
        attributes = newAttributes;
    }

    public Attribute getAttribute(int position){
        if(position < attributes.size() && position >= 0)
            return attributes.get(position);
        return null;
    }

    public List<Attribute> getAttributes(){
        return attributes;}

    
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

    public void deleteVariable(String variableName) {
        // TODO: deleteAttribute?
        int counter = 0;
        for (Attribute attribute: attributes) {
            if(variableName == attribute.getName()) {
                attributes.remove(counter);
                break;
            }
            counter++;
        }
    }

    public void setModifiers(Set<Modifier> modifiers){
        this.modifiers = modifiers;
    }

    public void addModifier(Modifier modifier){
        modifiers.add(modifier);
    }

    public Set<Modifier> getModifiers(){
        return modifiers;}

    public void removeModifier(Modifier modifier){
        // TODO: deleteModifier? (consistency)
        modifiers.remove(modifier);
    }

    public void setVisibility(Visibility visibility){
        this.visibility = visibility;
    }

    public Visibility getVisibility(){
        return visibility;
    }

    public void setPosition(ScaledPoint newPosition){
        position = newPosition;
    }

    public ScaledPoint getPosition(){
        return position; }

    public int getHeight() {
        int count = getMethods().size() + getAttributes().size();
        int symbolheight = 10;
        return (count)*symbolheight;
    }
    public int getWidth() {
        ArrayList<Integer> longest = new ArrayList<>();
        longest.add(getName().length());
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < getMethods().size(); i++) {
            names.add(getMethods().get(i).getName());
        }
        for (int i = 0; i < getAttributes().size(); i++) {
            names.add(getAttributes().get(i).getName());
        } 
        for (int i = 0; i < names.size(); i++) {
            longest.add(names.get(i).length());
        }
        return Collections.max(longest);
    }
}  
