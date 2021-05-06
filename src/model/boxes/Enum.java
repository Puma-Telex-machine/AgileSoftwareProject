package model.boxes;

import model.point.Scale;
import model.point.ScaledPoint;

import java.awt.*;

public class Enum extends Box {

    public Enum(ScaledPoint position, String name) {
        super(name, position);
    }

    @Override
    public BoxType getType(){
        return BoxType.ENUM;
    }
}
