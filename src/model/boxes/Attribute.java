package model.boxes;

import model.facades.AttributeFacade;
import model.facades.VariableData;

import java.util.HashSet;
import java.util.Set;

/** Represents either a method argument or a variable.
 *  Originally created by Emil Holmsten,
 *  Updated by Filip Hanberg.
 */
public class Attribute implements AttributeFacade {

    private String name;
    private Set<Modifier> modifiers = new HashSet<>();
    private Visibility visibility;

    enum Type { //Probably requires non-enum solution
        INT
    }

    public Attribute(String name, Set<Modifier> modifiers, Visibility visibility){
        this.name = name;
        this.modifiers = modifiers;
        this.visibility = visibility;
    }

    Attribute(VariableData data){
        this.name = data.name;
        this.visibility = data.visibility;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setVisibility(Visibility visibility){
        this.visibility = visibility;
    }

    /**
     * Changes all of the Attribute's modifiers.
     * @param modifiers The new set of modifiers.
     *  */
    public void setModifiers(Set<Modifier> modifiers){
        this.modifiers = modifiers;
    }

    /**
     * Adds a modifier to the Attribute.
     * @param modifier The modifier to be added.
     */
    public void addModifier(Modifier modifier){
        modifiers.add(modifier);
    }

    /**
     * Removes a modifier from the Attribute.
     * @param modifier The modifier to be removed.
     */
    public void removeModifier(Modifier modifier){
        modifiers.remove(modifier);
    }

    public String getName(){
        return name;
    }

    public Set<Modifier> getModifiers(){
        return modifiers;
    }

    public Visibility getVisibility(){
        return visibility;
    }



}
