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

    
    /** 
     * Set the name of the metod
     * @param name
     */
    @Override
    public void setName(String name) {
        this.name = name;
        update();
    }

    
    /** 
     * Gets the name of the metod
     * @return String
     */
    @Override
    public String getName() {
        return name;
    }

    
    /** 
     * Sets the type of metod
     * @param type
     */
    @Override
    public void setType(String type) {
        returnType = type;
        update();
    }

    
    /** 
     * Gets the type of method
     * @return String
     */
    @Override
    public String getType() {
        return returnType;
    }

    @Override
    public void removeAllArguments() {
        parameters.clear();
        update();
    }

    /**
     * Adds an argument to the Method.
     * @param argument The argument to be added.
     */
    @Override
    public void addArgument(String argument) {
        parameters.add(argument);
        update();
    }

    /**
     * Removes an argument from the Method
     * @param argument the argument to be removed.
     */
    @Override
    public void removeArgument(String argument) {
        parameters.remove(argument);
        update();
    }

    
    /** 
     * Returns a list of parameters
     * @return List<String>
     */
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
        update();
    }

    /**
     * Removes a modifier from the Method.
     * @param modifier The modifier to be removed.
     */
    @Override
    public void removeModifier(Modifier modifier) {
        modifiers.remove(modifier);
        update();
    }

    
    /** 
     * Gets the set of modifiers
     * @return Set<Modifier>
     */
    @Override
    public Set<Modifier> getModifiers() {
        return modifiers;
    }

    
    /** 
     * Set the visibility of the box
     * @param visibility
     */
    @Override
    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
        update();
    }

    
    /** 
     * Get the visibilith of the box
     * @return Visibility
     */
    @Override
    public Visibility getVisibility() {
        return visibility;
    }

    UiObserver observer;
    private Boolean ignoreObserver = false; //used by database
    
     /** 
     * TODO: shouldn't ot be setsubscriber or something
     * @param observer
     */
    @Override
    public void subscribe(UiObserver observer) {
       this.observer = observer;
    }

    /**
     * Stop updating observer
     */
    public void ignoreObserver(){
        ignoreObserver = true;
    }

    /**
     * Updates the observer
     */
    public void stopIgnore(){
        ignoreObserver = false;
    }

    /**
     * Updates observer if it is set to false
     */
    private void update(){
        if(!ignoreObserver)
            observer.update();
    }

    
   /** 
     * Turns the visibility, name and parameteres into a String and return it as a metod
     * @return String
     */
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
