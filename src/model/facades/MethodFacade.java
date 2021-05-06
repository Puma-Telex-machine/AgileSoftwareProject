package model.facades;

import model.boxes.Modifier;
import model.boxes.Visibility;

import java.util.List;
import java.util.Set;

public interface MethodFacade {
    void setName(String name);

    void addArgument(String argument);

    void removeArgument(String argument);

    void addModifier(Modifier modifier);

    void removeModifier(Modifier modifier);

    void setVisibility(Visibility visibility);

    String getName();

    List<String> getParameters();

    Set<Modifier> getModifiers();

    Visibility getVisibility();
}
