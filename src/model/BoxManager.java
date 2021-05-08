/*package model;

import frontend.Observers.UiObserver;
import model.boxes.Attribute;
import model.boxes.Box;
import model.boxes.BoxType;
import model.boxes.Method;
import model.facades.BoxFacade;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BoxManager implements BoxFacade {
    private final Diagram diagram;
    private final Box box;
    private ArrayList<UiObserver> observers = new ArrayList<UiObserver>();

    public void subscribe(UiObserver newObserver)
    {
        observers.add(newObserver);
    }

    private  void updateObservers()
    {
        for (int i = 0; i < observers.size(); i++)
        {
            observers.get(i).update();
        }
    }

    public BoxManager(Diagram diagram, Point position) {
        this.diagram = diagram;
        this.box = diagram.createBox(position);
    }

    public BoxManager(Diagram diagram, Box box){
        this.diagram = diagram;
        this.box = box;
    }

    @Override
    public void deleteBox() {
        diagram.deleteBox(box);
    }

    @Override
    public void editMethod(MethodData methodData) {

        diagram.editMethod(box, methodData);
        updateObservers();
    }

    @Override
    public void editVariable(VariableData variableData) {

        diagram.editVariable(box, variableData);
        updateObservers();
    }

    @Override
    public void deleteMethod(String methodName) {
        diagram.deleteMethod(box, methodName);
        updateObservers();
    }

    @Override
    public void deleteVariable(String variableName) {
        diagram.deleteVariable(box, variableName);
        updateObservers();
    }



    @Override
    public Point getPosition() {
        return new Point(box.getPosition());
    }

    @Override
    public void setPosition(Point point) {
        diagram.move(box, point);
    }

    @Override
    public void setName(String name) {
        box.setName(name);
    }

    @Override
    public BoxType getType() {
        return box.getType();
    }

    @Override
    public VariableData[] getVariables() {
        List<Attribute> variables = box.getAttributes();
        if(variables.size() != 0){
            VariableData[] result = new VariableData[variables.size()];
            for(int i = 0; i < variables.size() ; i++){
                result[i] = new VariableData();
                result[i].name = variables.get(i).getName();
                result[i].visibility = variables.get(i).getVisibility();
                result[i].variableType = "int"; // this doesn't quite work yet. Do just save user input as a string?
            }
            return result;
        }
        return new VariableData[0];
    }

    @Override
    public MethodData[] getMethods() {
        List<Method> methods = box.getMethods();
        if(methods.size() != 0){
            MethodData[] result = new MethodData[methods.size()];
            for(int i = 0; i < methods.size() ; i++){
                result[i] = new MethodData();
                result[i].methodName = methods.get(i).getName();
                result[i].methodReturnType = "int"; // this doesn't quite work yet. Do just save user input as a string?
                result[i].visibility = methods.get(i).getVisibility();
                result[i].arguments = getMethodParams(methods.get(i));
            }
            return result;
        }
        return new MethodData[0];
    }

    private String[] getMethodParams(Method method){
        List<Attribute> params = method.getParameters();
        if(params.size() != 0) {
            String[] result = new String[params.size()];
            for (int i = 0; i < params.size(); i++){
                result[i] = params.get(i).getName();
            }
            return result;
        }
        return new String[0];
    }

    public boolean isEmpty() {
        return box == null;
    }
}

*/
