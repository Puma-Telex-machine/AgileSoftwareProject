package model.boxes;

import model.facades.MethodData;
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
    //String returnValue; // Unsure how to implement types, for now

    /*
    public Method(String name, List<Attribute> parameters, Set<Modifier> modifiers, Visibility visibility){
        this.name = name;
        this.parameters = parameters;
        this.modifiers = modifiers;
        this.visibility = visibility;
    }

    Method(MethodData data){
        this.name = data.methodName;
        this.parameters = createArguments(data);
        this.visibility = data.visibility;
    }


    private List<Attribute> createArguments(MethodData methodData){
        List<Attribute> result = new ArrayList<>();
        for (String argument: methodData.arguments) {
            result.add(new Attribute(argument,null, null));
        }
        return result;
    }
     */

    @Override
    public void setName(String name){
        this.name = name;
    }

    /**
     * Adds an argument to the Method.
     * @param argument The argument to be added.
     */
    @Override
    public void addArgument(String argument){
        parameters.add(argument);
    }

    /**
     * Removes an argument from the Method
     * @param position the argument's position in the list.
     */
    @Override
    public void removeArgument(String argument){
        parameters.remove(argument);
    }

    /**
     * Adds a modifier to the Method.
     * @param modifier The modifier to be added.
     */
    @Override
    public void addModifier(Modifier modifier){
        modifiers.add(modifier);
    }

    /**
     * Removes a modifier from the Method.
     * @param modifier The modifier to be removed.
     */
    @Override
    public void removeModifier(Modifier modifier){
        modifiers.remove(modifier);
    }

    @Override
    public void setVisibility(Visibility visibility){
        this.visibility = visibility;
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public List<String> getParameters(){
        return parameters;
    }

    @Override
    public Set<Modifier> getModifiers(){
        return modifiers;
    }

    @Override
    public Visibility getVisibility(){
        return visibility;
    }
}
