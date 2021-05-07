package model.facades;

import model.boxes.Modifier;
import model.boxes.Visibility;

import java.util.List;
import java.util.Set;

public interface MethodFacade {

    void setName(String name);
    String getName();

    void addArgument(String argument);
    void removeArgument(String argument);
    List<String> getArguments();

    void addModifier(Modifier modifier);
    void removeModifier(Modifier modifier);
    Set<Modifier> getModifiers();

    void setVisibility(Visibility visibility);
    Visibility getVisibility();
}
