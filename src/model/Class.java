package model;

import java.awt.*;

/**
 * Represents a class. Subclass of the "Box" class.
 * Originally created by Emil Holmsten,
 * Updated by Filip Hanberg.
 */
public class Class extends Box {

    public Class(Point position, String name) {
        super(position, name);
    }

    @Override
    public String getType(){
        if(getModifiers().contains(Modifier.ABSTRACT))
            return "AbstractClass";
        return "Class";
    }
}
