package model.facades;

import model.boxes.Box;
import model.point.ScaledPoint;
//import model.facades.VariableData;

import java.awt.*;

public interface BoxFacade {

    //TODO: DELETE THIS

    Box getBox();

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
     * If a variable with this name exists then delete that variable.
     * If the variable doesn't exist then do nothing.
     * @param variableName The name of the variable that should be deleted.
     * @return
     */
    void deleteVariable(String variableName);

    ScaledPoint getPosition();

    void setPosition(ScaledPoint point);

    void setName(String name);
}
