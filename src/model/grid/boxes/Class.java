package model.grid.boxes;

import java.awt.*;

/**
 * Represents a class. Subclass of the "Box" class.
 * Originally created by Emil Holmsten,
 * Updated by Filip Hanberg.
 */
public class Class extends Box {

    public Class(Point position, String name) {
        super(name, position);
    }

    @Override
    public BoxType getType(){
        if(getModifiers().contains(Modifier.ABSTRACT))
            return BoxType.ABSTRACTCLASS;
        return BoxType.CLASS;
    }
}
