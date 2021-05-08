package model.facades;

import frontend.Observers.UiObservable;
import frontend.Observers.UiObserver;
import model.MethodData;
import model.VariableData;
import model.boxes.BoxType;
//import model.VariableData;

import java.awt.*;

public interface BoxFacade extends UiObservable {

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
     * @return
     */
    void deleteMethod(String methodName);

    /**
     * Deletes this variable if the variable exists on the box.
     * If the variable doesn't exist then do nothing
     * @param variable
     */
    void deleteVariable(AttributeFacade variable);

    /**
     * Deletes this method if it exists on the box.
     * If it doesn't exist then do nothing
     * @param method
     */
    void deleteMethod(MethodFacade method);

    Point getPosition();

    void setPosition(Point point);

    void setName(String name);

    BoxType getType();

    /**
     * Returns the variables of this class
     * @returns
     */
    AttributeFacade[] getVariables();

    /**
     * Returns the methods of this class
      * @return
     */
    MethodFacade[] getMethods();

    /**
     * Adds a new variable to the box and returns it
     * @return
     */
    AttributeFacade addVairable();

    /**
     * Adds a new method to the box and returns it
     * @return
     */
    MethodFacade addMethod();
}
