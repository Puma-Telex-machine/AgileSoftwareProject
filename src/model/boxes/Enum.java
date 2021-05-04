package model.boxes;

import model.point.ScaledPoint;

import java.awt.*;

public class Enum extends Box {

    public Enum(ScaledPoint position, String name) {
        super(name, position);
    }

    @Override
    public BoxType getType(){
        if(getModifiers().contains(Modifier.ABSTRACT))
            return BoxType.ABSTRACTCLASS;
        return BoxType.CLASS;
    }
}
