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
    private final Set<Modifier> modifiers = new HashSet<>();
    private Visibility visibility;

    /*
    enum Type { //Probably requires non-enum solution
        INT
    }

    public Attribute(){
        this.name = "attribute";
        this.visibility = Visibility.PACKAGE_PRIVATE;
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
     */

    @Override
    public void setName(String name){
        this.name = name;
    }

    @Override
    public void setVisibility(Visibility visibility){
        this.visibility = visibility;
    }

    /**
     * Adds a modifier to the Attribute.
     * @param modifier The modifier to be added.
     */
    @Override
    public void addModifier(Modifier modifier){
        modifiers.add(modifier);
    }

    /**
     * Removes a modifier from the Attribute.
     * @param modifier The modifier to be removed.
     */
    @Override
    public void removeModifier(Modifier modifier){
        modifiers.remove(modifier);
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public Set<Modifier> getModifiers(){
        return modifiers;
    }

    @Override
    public Visibility getVisibility(){
        return visibility;
    }
}
