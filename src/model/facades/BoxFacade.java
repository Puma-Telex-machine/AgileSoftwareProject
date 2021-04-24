package model.facades;

import model.MethodData;
import model.VariableData;

import java.awt.*;

public interface BoxFacade {
    /**
     * Should edit the method if the method already exists.
     * If the method doesn't exist the method should be added instead.
     * @param methodData The data for the new method. Should replace the old data.
     */
    public void EditMethod(MethodData methodData);

    /**
     * Should edit the variable if the variable exists.
     * If the variable doesn't exist the variable should be added instead.
     * @param variableData The data for the new variable. Should replace the old data.
     */
    public void EditVariable(VariableData variableData);

    /**
     * If a method with this name exists then delete the method.
     * If the method doesn't exist then do nothing.
     * @param methodName The name of the method that should be deleted
     */
    public void DeleteMethod(String methodName);

    /**
     * If a variable with this name exists then delete that variable.
     * If the variable doesn't exist then do nothing.
     * @param variableName The name of the variable that should be deleted.
     */
    public void DeleteVariable(String variableName);

    public Point getPosition();

    void setPosition(Point point);
}
