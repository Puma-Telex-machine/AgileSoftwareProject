package model.boxes;

import model.point.Scale;
import model.point.ScaledPoint;

import java.awt.*;

public class Interface extends Box {
    public Interface(ScaledPoint position, String name) {
        super(name, position);
    }
    @Override
    public BoxType getType(){
        return BoxType.INTERFACE;
    }
}
