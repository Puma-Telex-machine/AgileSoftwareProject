package model.grid;

import model.boxes.Box;
import model.point.Scale;
import model.point.ScaledPoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BoxGrid2 {

    ArrayList<Box> boxes = new ArrayList<>();

    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    public boolean isOccupied(ScaledPoint scaledPoint) {
        for (Box b : boxes) {
            if (contains(b, scaledPoint)) return true;
        }
        return false;
    }

    public void update(Box box) {
        for (Box b : boxes) {
            if (b != box) {
                if (isOverlapping(box, b)) {
                    ScaledPoint justBelowBox = new ScaledPoint(Scale.Backend, b.getPosition().getX(Scale.Backend), box.getPosition().getY(Scale.Backend) + box.getWidthAndHeight().getY(Scale.Backend));
                    b.setPosition(justBelowBox);
                }
            }
        }
    }

    boolean contains(Box box, ScaledPoint point) {
        ScaledPoint topLeft = box.getPosition();
        ScaledPoint bottomRight = topLeft.move(box.getWidthAndHeight());
        if (topLeft.getX(Scale.Backend) < point.getX(Scale.Backend) && point.getX(Scale.Backend) < bottomRight.getX(Scale.Backend)) {
            if (topLeft.getY(Scale.Backend) < point.getY(Scale.Backend) && point.getY(Scale.Backend) < bottomRight.getY(Scale.Backend)) {
                return true;
            }
        }
        return false;
    }

    boolean isOverlapping(Box b1, Box b2) {
        if (getTopLeft(b1).getY(Scale.Backend) < getBottomRight(b2).getY(Scale.Backend)
                || getBottomRight(b1).getY(Scale.Backend) > getTopLeft(b2).getY(Scale.Backend)) {
            return false;
        }
        if (getTopLeft(b1).getX(Scale.Backend) < getBottomRight(b2).getX(Scale.Backend)
                || getBottomRight(b1).getX(Scale.Backend) > getTopLeft(b2).getX(Scale.Backend)) {
            return false;
        }
        return true;
    }

    private ScaledPoint getTopLeft(Box box) {
        return box.getPosition();
    }

    private ScaledPoint getBottomRight(Box box) {
        return box.getPosition().move(box.getWidthAndHeight());
    }
}