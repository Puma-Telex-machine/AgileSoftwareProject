package model.boxes;

import global.Observable;
import global.Observers;
import global.Observer;
import model.facades.MethodFacade;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a method within a class/interface
 * Originally created by Emil Holmsten,
 * Updated by Filip Hanberg.
 */
public class Method implements MethodFacade, Observable<Observer> {

    private String name = "method";
    private final List<String> parameters = new ArrayList<>();
    private final Set<Modifier> modifiers = new HashSet<>();
    private Visibility visibility = Visibility.PUBLIC;
    private String returnType = "void";

    //region OBSERVABLE
    Observers observers = new Observers();

    @Override
    public void subscribe(Observer observer) {
        observers.add(observer);
    }
    //endregion

    @Override
    public void setName(String name) {
        this.name = name;
        observers.update();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setType(String type) {
        returnType = type;
        observers.update();
    }

    @Override
    public String getType() {
        return returnType;
    }

    @Override
    public void removeAllArguments() {
        parameters.clear();
        observers.update();
    }

    /**
     * Adds an argument to the Method.
     * @param argument The argument to be added.
     */
    @Override
    public void addArgument(String argument) {
        parameters.add(argument);
        observers.update();
    }

    /**
     * Removes an argument from the Method
     * @param argument the argument to be removed.
     */
    @Override
    public void removeArgument(String argument) {
        parameters.remove(argument);
        observers.update();
    }

    @Override
    public List<String> getArguments() {
        return parameters;
    }

    /**
     * Adds a modifier to the Method.
     * @param modifier The modifier to be added.
     */
    @Override
    public void addModifier(Modifier modifier) {
        modifiers.add(modifier);
        observers.update();
    }

    /**
     * Removes a modifier from the Method.
     * @param modifier The modifier to be removed.
     */
    @Override
    public void removeModifier(Modifier modifier) {
        modifiers.remove(modifier);
        observers.update();
    }

    @Override
    public Set<Modifier> getModifiers() {
        return modifiers;
    }

    @Override
    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
        observers.update();
    }

    @Override
    public Visibility getVisibility() {
        return visibility;
    }

    /*UiObserver observer;
    private Boolean ignoreObserver = false; //used by database
    @Override
    public void subscribe(UiObserver observer) {
       this.observer = observer;
    }

    public void ignoreObserver(){
        ignoreObserver = true;
    }

    public void stopIgnore(){
        ignoreObserver = false;
    }

    private void update(){
        if(!ignoreObserver)
            observer.update();
    }*/

    @Override
    public String getString(){
        String method = "";
        method += Visibility.getString(visibility);
        method += " ";
        method += name;
        method += " (";
        List<String> param = parameters;
        for (int j = 0; j < param.size(); j++) {
            method += param.get(j);

            if (j + 1 != param.size())
                method += ", ";
        }
        method += ") : ";
        method += returnType;

        return method;
    }
}
