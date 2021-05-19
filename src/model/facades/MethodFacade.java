package model.facades;

import model.boxes.Modifier;
import model.boxes.Visibility;

import java.util.List;
import java.util.Set;

public interface MethodFacade extends UndoChain {

    void setName(String name);
    String getName();

    /**
     * Sets the return type of the method
     * @param type
     */
    void setType(String type);

    /**
     * Returns the return type of the method
     * @return
     */
    String getType();

    /**
     * Completely clears the previous arguments
     */
    void removeAllArguments();
    void addArgument(String argument);
    void removeArgument(String argument); //FYI I don't need this / Emil E
    List<String> getArguments();

    void addModifier(Modifier modifier); //Please explain what this is / Emil E
    void removeModifier(Modifier modifier);
    Set<Modifier> getModifiers();

    void setVisibility(Visibility visibility);
    Visibility getVisibility();

    String getString();
}
