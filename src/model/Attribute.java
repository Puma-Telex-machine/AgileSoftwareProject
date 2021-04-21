package model;

import java.util.Set;

public class Attribute {

    String name;
    Set<Modifier> modifiers;
    Visibility visibility;

    enum Type{ //Probably requires non-enum solution
        INT
    }

    Attribute(String name, Set<Modifier> modifiers, Visibility visibility){
        this.name = name;
        this.modifiers = modifiers;
        this.visibility = visibility;
    }

    void SetName(String name){
        this.name = name;
    }

    void SetVisibility(Visibility visibility){
        this.visibility = visibility;
    }

    /**
     * Changes all of the Attribute's modifiers.
     * @param modifiers the new set of modifiers.
     *  */
    void SetModifiers(Set<Modifier> modifiers){
        this.modifiers = modifiers;
    }

    /**
     * Adds a modifier to the Attribute.
     * @param modifier the modifier to be added.
     */
    void AddModifier(Modifier modifier){
        modifiers.add(modifier);
    }

    /**
     * Removes a modifier from the Attribute.
     * @param modifier The modifier to be removed.
     */
    void RemoveModifier(Modifier modifier){
        modifiers.remove(modifier);
    }

    String GetName(){
        return name;
    }

    Set<Modifier> GetModifiers(){
        return modifiers;
    }

    Visibility GetVisibility(){
        return visibility;
    }



}
