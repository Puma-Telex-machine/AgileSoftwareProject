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

    @Override
    public void setName(String name) {
        this.name = name;
        observer.update();
    }

    @Override
    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
        observer.update();
    }

    /**
     * Adds a modifier to the Attribute.
     * @param modifier The modifier to be added.
     */
    @Override
    public void addModifier(Modifier modifier) {
        modifiers.add(modifier);
        observer.update();
    }

    /**
     * Removes a modifier from the Attribute.
     * @param modifier The modifier to be removed.
     */
    @Override
    public void removeModifier(Modifier modifier) {
        modifiers.remove(modifier);
        observer.update();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setType(String type) {
        this.type = type;
        observer.update();
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Set<Modifier> getModifiers() {
        return modifiers;
    }

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
