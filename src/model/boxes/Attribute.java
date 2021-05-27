package model.boxes;

import global.Observable;
import global.Observers;
import global.Observer;
import model.facades.AttributeFacade;
import model.facades.UndoChain;

import java.util.HashSet;
import java.util.Set;

/** Represents either a method argument or a variable.
 *  Originally created by Emil Holmsten,
 *  Updated by Filip Hanberg.
 */
public class Attribute implements AttributeFacade, Observable<Observer> {

    private String name = "foo";
    private final Set<Modifier> modifiers = new HashSet<>();
    private Visibility visibility = Visibility.PRIVATE;
    private String type = "int";
    private boolean isConfirmed = false;

    public Attribute(UndoChain undoChain){
        this.undoChain = undoChain;
    }
    public Attribute() {}
    //region OBSERVABLE
    Observers observers = new Observers();
    private UndoChain undoChain;
    private Boolean undoActive = true;

    @Override
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void stopUpdates(){observers.stopUpdates();}

    public void startUpdates(){observers.startUpdates();}

    @Override
    public void updateUndo() {
        if(undoActive)
            undoChain.updateUndo();
    }

    @Override
    public void stopUndo() {
        undoActive = false;
    }

    @Override
    public void resumeUndo() {
        undoActive = true;
    }
    //endregion

    
    /** 
     * Set name for attribute
     * @param name
     */
    @Override
    public void setName(String name) {
        this.name = name;
        observers.update();
        updateUndo();
    }

    
    /** 
     * Set visibility for attribute
     * @param visibility
     */
    @Override
    public void confirmAttribute()
    {
        isConfirmed = true;
    }

    public boolean getConfirmed()
    {
        return isConfirmed;
    }
    
    @Override
    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
        observers.update();
        updateUndo();
    }

    /**
     * Adds a modifier to the Attribute.
     * @param modifier The modifier to be added.
     */
    @Override
    public void addModifier(Modifier modifier) {
        modifiers.add(modifier);
        observers.update();
        updateUndo();
    }

    /**
     * Removes a modifier from the Attribute.
     * @param modifier The modifier to be removed.
     */
    @Override
    public void removeModifier(Modifier modifier) {
        modifiers.remove(modifier);
        observers.update();
        updateUndo();
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
        observers.update();
        updateUndo();
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

    /*UiObserver observer;
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
    }*/
}
