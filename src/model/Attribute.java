package model;

import java.util.Set;

/** Represents either a method argument or a variable.
 *  Originally created by Emil Holmsten,
 *  Updated by Filip Hanberg.
 */
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

    public void SetName(String name){
        this.name = name;
    }

    public void SetVisibility(Visibility visibility){
        this.visibility = visibility;
    }

    /**
     * Changes all of the Attribute's modifiers.
     * @param modifiers The new set of modifiers.
     *  */
    public void SetModifiers(Set<Modifier> modifiers){
        this.modifiers = modifiers;
    }

    /**
     * Adds a modifier to the Attribute.
     * @param modifier The modifier to be added.
     */
    public void AddModifier(Modifier modifier){
        modifiers.add(modifier);
    }

    /**
     * Removes a modifier from the Attribute.
     * @param modifier The modifier to be removed.
     */
    public void RemoveModifier(Modifier modifier){
        modifiers.remove(modifier);
    }

    public String GetName(){
        return name;
    }

    public Set<Modifier> GetModifiers(){
        return modifiers;
    }

    public Visibility GetVisibility(){
        return visibility;
    }



}
