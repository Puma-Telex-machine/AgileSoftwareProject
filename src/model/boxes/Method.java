package model.boxes;

import model.MethodData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a method within a class/interface
 * Originally created by Emil Holmsten,
 * Updated by Filip Hanberg.
 */
public class Method {

    private String name;
    private List<Attribute> parameters = new ArrayList<>();
    private Set<Modifier> modifiers = new HashSet<>();
    private Visibility visibility = Visibility.PUBLIC;
    String returnValue; // Unsure how to implement types, for now

    public Method(String name, List<Attribute> parameters, Set<Modifier> modifiers, Visibility visibility){
        this.name = name;
        this.parameters = parameters;
        this.modifiers = modifiers;
        this.visibility = visibility;
    }

    Method(MethodData data){
        this.name = data.methodName;
        this.parameters = createArguments(data);
        this.visibility = data.visibility;
    }

    private List<Attribute> createArguments(MethodData methodData){
        List<Attribute> result = new ArrayList<>();
        for (String argument: methodData.arguments) {
            result.add(new Attribute(argument,null, null));
        }
        return result;
    }

    public void setName(String name){
        this.name = name;
    }

    /**
     * Changes all of the Method's arguments.
     * @param data The new set of arguments.
     */
    public void SetArguments(MethodData data){
        this.parameters = createArguments(data);
    }

    /**
     * Adds an argument to the Method.
     * @param argument The argument to be added.
     */
    public void AddArgument(Attribute argument){
        parameters.add(argument);
    }

    /**
     * Removes an argument from the Method
     * @param position the argument's position in the list.
     */
    public void RemoveArgument(int position){
        if(position < parameters.size() && position >= 0)
            parameters.remove(position);
    }

    /**
     * Changes all of the Method's modifiers.
     * @param modifiers The new set of modifiers.
     *  */
    public void SetModifiers(Set<Modifier> modifiers){
        this.modifiers = modifiers;
    }

    /**
     * Adds a modifier to the Method.
     * @param modifier The modifier to be added.
     */
    public void AddModifier(Modifier modifier){
        modifiers.add(modifier);
    }

    /**
     * Removes a modifier from the Method.
     * @param modifier The modifier to be removed.
     */
    public void RemoveModifier(Modifier modifier){
        modifiers.remove(modifier);
    }

    public void SetVisibility(Visibility visibility){
        this.visibility = visibility;
    }

    public String getName(){
        return name;
    }

    public List<Attribute> getParameters(){
        return parameters;
    }

    public Set<Modifier> getModifiers(){
        return modifiers;
    }

    public Visibility getVisibility(){
        return visibility;
    }
}
