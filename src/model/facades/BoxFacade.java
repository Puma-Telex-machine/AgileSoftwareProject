package model.facades;

import model.boxes.*;
import model.point.ScaledPoint;

import java.util.List;
import java.util.Set;

public interface BoxFacade {

    /**
     * Delete this box from the diagram.
     */
    void deleteBox();

    /**
     * Should edit the method if the method already exists.
     * If the method doesn't exist the method should be added instead.
     * @param methodData The data for the new method. Should replace the old data.
     */
    void editMethod(MethodData methodData);

    /**
     * Should edit the variable if the variable exists.
     * If the variable doesn't exist the variable should be added instead.
     * @param variableData The data for the new variable. Should replace the old data.
     */
    void editVariable(VariableData variableData);

    /**
     * If a method with this name exists then delete the method.
     * If the method doesn't exist then do nothing.
     * @param methodName The name of the method that should be deleted
     */
    void deleteMethod(String methodName);

    /**
     * If a variable with this name exists then delete that variable.
     * If the variable doesn't exist then do nothing.
     * @param variableName The name of the variable that should be deleted.
     */
    void deleteVariable(String variableName);

    /**
     * Get the position of the box.
     * @return the position as a ScaledPoint
     */
    ScaledPoint getPosition();

    void removeModifier(Modifier modifier);

    void setVisibility(Visibility visibility);

    Visibility getVisibility();

    /**
     * Set the position of the box.
     * @param point the position to move the box to.
     */
    void setPosition(ScaledPoint point);

    void addModifier(Modifier modifier);

    Set<Modifier> getModifiers();

    /**
     * Get the name of the box.
     * @return the name of the box.
     */
    String getName();

    /**
     * Set the name of the box.
     * @param name the name of the box.
     */
    void setName(String name);

    /**
     * Get the box type (Class, Interface, Enum).
     * @return the name of the box.
     */
    BoxType getType();

    /**
     * Returns the data for all of the methods of this class
     */
    List<AttributeFacade> getAttributes();

    /**
     * Returns the data for all of the variables of this class
     * @return
     */
    List<MethodFacade> getMethods();

    ScaledPoint getWidthAndHeight();
}
