package model.facades;

import model.boxes.Modifier;
import model.boxes.Visibility;

import java.util.Set;

public interface AttributeFacade {

    void setName(String name);
    String getName();

    void setVisibility(Visibility visibility);
    Visibility getVisibility();

    void addModifier(Modifier modifier);
    void removeModifier(Modifier modifier);
    Set<Modifier> getModifiers();
}
