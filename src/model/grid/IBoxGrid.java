package model.grid;

import model.boxes.Box;
import model.point.ScaledPoint;

import java.awt.*;

public interface IBoxGrid {
    void move(Box box, ScaledPoint point);
    void remove(Box box);
    Box createBox(ScaledPoint point);
    void update(Box box);
}
