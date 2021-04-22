package model.boxes;

import java.awt.*;

public class Enum extends Box {

    public Enum(Point position, String name) {
        super(position, name);
    }

    @Override
    public BoxType getType(){
        if(getModifiers().contains(Modifier.ABSTRACT))
            return BoxType.ABSTRACTCLASS;
        return BoxType.CLASS;
    }
}
