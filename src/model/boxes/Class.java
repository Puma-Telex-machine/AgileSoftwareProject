package model.boxes;

import model.point.Scale;
import model.point.ScaledPoint;

import java.awt.*;

/**
 * Represents a class. Subclass of the "Box" class.
 * Originally created by Emil Holmsten,
 * Updated by Filip Hanberg.
 */
public class Class extends Box {

    public Class(Point position, String name) {
        super(name, new ScaledPoint(Scale.Frontend,position)); //todo: fix scale
    }

    @Override
    public BoxType getType(){
        if(getModifiers().contains(Modifier.ABSTRACT))
            return BoxType.ABSTRACTCLASS;
        return BoxType.CLASS;
    }
}
