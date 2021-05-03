package model.boxes;

import model.point.Scale;
import model.point.ScaledPoint;

import java.awt.*;

public class Interface extends Box {
    public Interface(Point position, String name) {
        super(name, new ScaledPoint(Scale.Internal,position));
    }
    @Override
    public BoxType getType(){
        return BoxType.INTERFACE;
    }
}
