package model.grid;

import model.boxes.Box;
import model.point.Scale;
import model.point.ScaledPoint;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BoxGrid2 {

    ArrayList<Box> boxes = new ArrayList<>();

    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    public boolean isOccupied(ScaledPoint scaledPoint) {
        Point point = new Point(scaledPoint.getX(Scale.Backend), scaledPoint.getY(Scale.Backend));
        for (Box b : boxes) {
            if (getBounds(b).contains(point)) return true;
        }
        return false;
    }

    public void update(Box box) {
        if(!boxes.contains(box))
            boxes.add(box);
        Rectangle bounds = getBounds(box);

        for (Box b : boxes) {
            if (b != box) {
                if (bounds.intersects(getBounds(b))) {
                    ScaledPoint justBelowBox = new ScaledPoint(Scale.Backend, b.getPosition().getX(Scale.Backend), box.getPosition().getY(Scale.Backend) + box.getWidthAndHeight().getY(Scale.Backend));
                    b.setPosition(justBelowBox);
                }
            }
        }
    }

    public void remove(Box box) {
        boxes.remove(box);
    }

    private Rectangle getBounds(Box box) {

        ScaledPoint position = box.getPosition();
        ScaledPoint widthAndHeight = box.getWidthAndHeight();

        Rectangle rectangle = new Rectangle();

        rectangle.x = position.getX(Scale.Backend);
        rectangle.y = position.getY(Scale.Backend);
        rectangle.width = widthAndHeight.getX(Scale.Backend);
        rectangle.height = widthAndHeight.getY(Scale.Backend);

        return rectangle;
    }
}