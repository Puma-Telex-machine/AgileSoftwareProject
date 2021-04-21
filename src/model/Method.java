package model;

import java.util.List;
import java.util.Set;

public class Method {

    private String name;
    private List<Attribute> arguments;
    private Set<Modifier> modifiers;
    private Visibility visibility;
    String returnValue; // Unsure how to implement types, for now

    Method(String name, List<Attribute> arguments, Set<Modifier> modifiers, Visibility visibility){
        this.name = name;
        this.arguments = arguments;
        this.modifiers = modifiers;
        this.visibility = visibility;
    }

    void SetName(String name){
        this.name = name;
    }

    /**
     * Changes all of the Method's arguments.
     * @param arguments The new set of arguments.
     */
    void SetArguments(List<Attribute> arguments){
        this.arguments = arguments;
    }

    /**
     * Adds an argument to the Method.
     * @param argument The argument to be added.
     */
    void AddArgument(Attribute argument){
        arguments.add(argument);
    }

    /**
     * Removes an argument from the Method
     * @param position the argument's position in the list.
     */
    void RemoveArgument(int position){
        if(position < arguments.size() && position >= 0)
            arguments.remove(position);
    }

    /**
     * Changes all of the Method's modifiers.
     * @param modifiers The new set of modifiers.
     *  */
    void SetModifiers(Set<Modifier> modifiers){
        this.modifiers = modifiers;
    }

    /**
     * Adds a modifier to the Method.
     * @param modifier The modifier to be added.
     */
    void AddModifier(Modifier modifier){
        modifiers.add(modifier);
    }

    /**
     * Removes a modifier from the Method.
     * @param modifier The modifier to be removed.
     */
    void RemoveModifier(Modifier modifier){
        modifiers.remove(modifier);
    }

    void SetVisibility(Visibility visibility){
        this.visibility = visibility;
    }

    String GetName(){
        return name;
    }

    List<Attribute> GetArguments(){
        return arguments;
    }

    Set<Modifier> GetModifiers(){
        return modifiers;
    }

    Visibility GetVisibility(){
        return visibility;
    }
}
