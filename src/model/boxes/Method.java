package model.boxes;

import frontend.Observers.UiObservable;
import frontend.Observers.UiObserver;
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
public class Method implements MethodFacade, UiObservable {

    private String name = "method";
    private final List<String> parameters = new ArrayList<>();
    private final Set<Modifier> modifiers = new HashSet<>();
    private Visibility visibility = Visibility.PUBLIC;
    private String returnType = "void";

    @Override
    public void setName(String name) {
        this.name = name;
        observer.update();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setType(String type) {
        returnType = type;
        observer.update();
    }

    @Override
    public String getType() {
        return returnType;
    }

    @Override
    public void removeAllArguments() {
        parameters.clear();
        observer.update();
    }

    /**
     * Adds an argument to the Method.
     * @param argument The argument to be added.
     */
    @Override
    public void addArgument(String argument) {
        parameters.add(argument);
        observer.update();
    }

    /**
     * Removes an argument from the Method
     * @param argument the argument to be removed.
     */
    @Override
    public void removeArgument(String argument) {
        parameters.remove(argument);
        observer.update();
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
        observer.update();
    }

    /**
     * Removes a modifier from the Method.
     * @param modifier The modifier to be removed.
     */
    @Override
    public void removeModifier(Modifier modifier) {
        modifiers.remove(modifier);
        observer.update();
    }

    @Override
    public Set<Modifier> getModifiers() {
        return modifiers;
    }

    @Override
    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
        observer.update();
    }

    @Override
    public Visibility getVisibility() {
        return visibility;
    }

    UiObserver observer;
    @Override
    public void subscribe(UiObserver observer) {
       this.observer = observer;
    }
}
