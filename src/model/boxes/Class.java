package model.boxes;

import model.point.ScaledPoint;

/**
 * Represents a class. Subclass of the "Box" class.
 * Originally created by Emil Holmsten,
 * Updated by Filip Hanberg.
 */
public class Class extends Box {

    public Class(ScaledPoint position, String name) {
        super(name, position); //todo: fix scale
    }

    @Override
    public BoxType getType(){
        if(getModifiers().contains(Modifier.ABSTRACT))
            return BoxType.ABSTRACT_CLASS;
        return BoxType.CLASS;
    }
}
