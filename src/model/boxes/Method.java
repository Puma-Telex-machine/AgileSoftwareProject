package model.boxes;

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
public class Method implements MethodFacade {

    private String name = "method";
    private final List<String> parameters = new ArrayList<>();
    private final Set<Modifier> modifiers = new HashSet<>();
    private Visibility visibility = Visibility.PUBLIC;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Adds an argument to the Method.
     * @param argument The argument to be added.
     */
    @Override
    public void addArgument(String argument) {
        parameters.add(argument);
    }

    /**
     * Removes an argument from the Method
     * @param argument the argument to be removed.
     */
    @Override
    public void removeArgument(String argument) {
        parameters.remove(argument);
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
    }

    /**
     * Removes a modifier from the Method.
     * @param modifier The modifier to be removed.
     */
    @Override
    public void removeModifier(Modifier modifier) {
        modifiers.remove(modifier);
    }

    @Override
    public Set<Modifier> getModifiers() {
        return modifiers;
    }

    @Override
    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    @Override
    public Visibility getVisibility() {
        return visibility;
    }
}
