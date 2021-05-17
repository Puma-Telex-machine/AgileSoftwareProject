package model.boxes;

import frontend.Observers.UiObservable;
import frontend.Observers.UiObserver;
import model.facades.AttributeFacade;
import model.facades.VariableData;

import java.util.HashSet;
import java.util.Set;

/** Represents either a method argument or a variable.
 *  Originally created by Emil Holmsten,
 *  Updated by Filip Hanberg.
 */
public class Attribute implements AttributeFacade, UiObservable {

    private String name = "foo";
    private final Set<Modifier> modifiers = new HashSet<>();
    private Visibility visibility = Visibility.PRIVATE;
    private String type = "int";

    
    /** 
     * Set name for attribute
     * @param name
     */
    @Override
    public void setName(String name) {
        this.name = name;
        update();
    }

    
    /** 
     * Set visibility for attribute
     * @param visibility
     */
    @Override
    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
        update();
    }

    /**
     * Adds a modifier to the Attribute.
     * @param modifier The modifier to be added.
     */
    @Override
    public void addModifier(Modifier modifier) {
        modifiers.add(modifier);
        update();
    }

    /**
     * Removes a modifier from the Attribute.
     * @param modifier The modifier to be removed.
     */
    @Override
    public void removeModifier(Modifier modifier) {
        modifiers.remove(modifier);
        update();
    }

    
    /** 
     * Returns name for the Attribute
     * @return String
     */
    @Override
    public String getName() {
        return name;
    }

    
    /** 
     * Set types for the attribute
     * @param type
     */
    @Override
    public void setType(String type) {
        this.type = type;
        update();
    }

    
    /** 
     * Get the attributes type
     * @return String
     */
    @Override
    public String getType() {
        return type;
    }

    
    /** 
     * Returns a Set of all modifiers
     * @return Set<Modifier>
     */
    @Override
    public Set<Modifier> getModifiers() {
        return modifiers;
    }

    
    /** 
     * Turns the visibility, name and type into a String and return it as a variable
     * @return String
     */
    @Override
    public String getString() {
        String variable = "";
        variable += Visibility.getString(visibility);
        variable += " ";
        variable += name;
        variable += ": ";
        variable += type;
        return variable;
    }

    
    /** 
     * get the visibility of the attribute
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
     * stop updating observer
     */
    public void ignoreObserver(){
        ignoreObserver = true;
    }

    /**
     * updates the observer
     */
    public void stopIgnore(){
        ignoreObserver = false;
    }

    /**
     * updates observer if it is set to false
     */
    private void update(){
        if(!ignoreObserver)
            observer.update();
    }
}
