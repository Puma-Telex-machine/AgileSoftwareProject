package model.facades;

import model.boxes.Modifier;
import model.boxes.Visibility;

import java.util.Set;

public interface AttributeFacade {

    void setName(String name);

    void setVisibility(Visibility visibility);

    void addModifier(Modifier modifier);

    void removeModifier(Modifier modifier);

    String getName();

    Set<Modifier> getModifiers();

    Visibility getVisibility();
}
