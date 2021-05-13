package model.diagram;

import model.boxes.Box;
import global.point.Scale;
import global.point.ScaledPoint;

import java.util.ArrayList;

public class BoxGrid2 {

    ArrayList<Box> boxes = new ArrayList<>();

    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    public void add(Box box) {
        boxes.add(box);
    }

    public void remove(Box box) {
        boxes.remove(box);
    }

    public boolean isOccupied(ScaledPoint scaledPoint) {
        for (Box b : boxes) {
            if (contains(b, scaledPoint)) {
                return true;
            }
        }
        return false;
    }

    public void update(Box box) {
        for (Box b : boxes) {
            if (b != box) {
                if (isOverlapping(box, b)) {
                    
                }
            }
        }
    }

    boolean contains(Box box, ScaledPoint point) {
        ScaledPoint topLeft = box.getPosition();
        ScaledPoint bottomRight = topLeft.move(box.getWidthAndHeight());

        topLeft = topLeft.move(Scale.Backend, -1, -1);
        bottomRight = bottomRight.move(Scale.Backend, 1, 1);

        if (topLeft.getX(Scale.Backend) < point.getX(Scale.Backend) && point.getX(Scale.Backend) < bottomRight.getX(Scale.Backend)) {
            if (topLeft.getY(Scale.Backend) < point.getY(Scale.Backend) && point.getY(Scale.Backend) < bottomRight.getY(Scale.Backend)) {
                return true;
            }
        }
        return false;
    }

    boolean isOverlapping(Box b1, Box b2) {
        if (getTopLeft(b1).getY(Scale.Backend) > getBottomRight(b2).getY(Scale.Backend)
                || getBottomRight(b1).getY(Scale.Backend) < getTopLeft(b2).getY(Scale.Backend)) {
            return false;
        }
        if (getTopLeft(b1).getX(Scale.Backend) > getBottomRight(b2).getX(Scale.Backend)
                || getBottomRight(b1).getX(Scale.Backend) < getTopLeft(b2).getX(Scale.Backend)) {
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