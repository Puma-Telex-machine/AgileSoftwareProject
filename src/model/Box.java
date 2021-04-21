package model;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Box {


    private String name;
    private Point position;
    private List<Method> methods;
    private List<Attribute> attributes;
    private Set<Modifier> modifiers;
    private Visibility visibility;

    public Box(Point position, String name) {
        this.position = position;
        this.name = name;
        methods = new ArrayList<>();
        attributes = new ArrayList<>();
        modifiers = new HashSet<>();
    }

    public void setName(String newName){
        name = newName;
    }

    public void setPosition(Point newPosition){
        position = newPosition;
    }

    public void setMethods(List<Method> newMethods){
        methods = newMethods;
    }

    public void addMethod(Method toAdd){
        methods.add(toAdd);
    }

    public void removeMethod(int position){
        if(position < methods.size() && position >= 0)
            methods.remove(position);
    }

    public void replaceMethod(int position, Method method){ //maybe unnecessary
        if(position < methods.size() && position >= 0) {
            methods.remove(position);
            methods.add(position, method);
        }
    }

    public Method getMethod(int position){
        if(position < methods.size() && position >= 0)
            return methods.get(position);
        return null;
    }

    public void setAttributes(List<Attribute> newAttributes){
        attributes = newAttributes;
    }

    public void addAttribute(Attribute toAdd){
        attributes.add(toAdd);
    }

    public void removeAttribute(int position){
        if(position < attributes.size() && position >= 0)
            attributes.remove(position);
    }

    public void replaceAttribute(int position, Attribute attribute){ //maybe unnecessary
        if(position < attributes.size() && position >= 0) {
            attributes.remove(position);
            attributes.add(position, attribute);
        }
    }

    public Attribute getAttribute(int position){
        if(position < attributes.size() && position >= 0)
            return attributes.get(position);
        return null;
    }

    void SetModifiers(Set<Modifier> modifiers){
        this.modifiers = modifiers;
    }

    void AddModifier(Modifier modifier){
        modifiers.add(modifier);
    }

    void RemoveModifier(Modifier modifier){
        modifiers.remove(modifier);
    }

    void SetVisibility(Visibility visibility){
        this.visibility = visibility;
    }

    public String getName() {
        return name;
    }

    public String getType(){
        return "no type";
    }
}
