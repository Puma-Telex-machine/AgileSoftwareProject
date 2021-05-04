package model.boxes;

import model.point.Scale;
import model.point.ScaledPoint;

import java.awt.*;

public class Enum extends Box {

    public Enum(Point position, String name) {
        super(name, new ScaledPoint(Scale.Internal,position));
    }

    @Override
    public BoxType getType(){
        if(getModifiers().contains(Modifier.ABSTRACT))
            return BoxType.ABSTRACTCLASS;
        return BoxType.CLASS;
    }
}
