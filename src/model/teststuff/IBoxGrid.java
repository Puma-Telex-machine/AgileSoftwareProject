package model.teststuff;

import model.boxes.Box;
import model.point.ScaledPoint;

public interface IBoxGrid {
    void move(Box box, ScaledPoint point);
    void remove(Box box);
}
