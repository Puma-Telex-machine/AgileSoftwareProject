package model.boxes;

import java.awt.*;

public class Interface extends Box {
    public Interface(Point position, String name) {
        super(position, name);
    }
    @Override
    public BoxType getType(){
        return BoxType.INTERFACE;
    }
}
