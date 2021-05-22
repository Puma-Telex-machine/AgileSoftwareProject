package model.facades;

import model.boxes.Modifier;
import model.boxes.Visibility;

import java.util.Set;

public interface AttributeFacade extends UndoChain {

    void setName(String name);
    String getName();

    /**
     * Sets the type of the variable
     * @param type
     */
    void setType(String type);

    /**
     * Returns the the type of the variable
     * @return
     */
    String getType();

    void setVisibility(Visibility visibility);
    Visibility getVisibility();

    /**
     * confirms that this attribute should exist.
     * An attribute that has never been confirm shouldn't be added to the frontend
     */
    void confirmAttribute();

    void addModifier(Modifier modifier); // Please explain what this is / Emil E
    void removeModifier(Modifier modifier);
    Set<Modifier> getModifiers();
    String getString();
}
