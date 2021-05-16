package model.diagram;

import model.boxes.Box;
import global.point.Scale;
import global.point.ScaledPoint;

import java.util.ArrayList;

public class BoxGrid2 {

    ArrayList<Box> boxes = new ArrayList<>();
    private final static int boxPadding = 1;

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
        if (!boxPositionIsValid(box)) {
            ScaledPoint topLeftTestBoxLeft = new ScaledPoint(getTopLeft(box));
            ScaledPoint bottomRightTestBoxLeft = new ScaledPoint(getBottomRight(box));
            ScaledPoint topLeftTestBoxRight = new ScaledPoint(topLeftTestBoxLeft);
            ScaledPoint bottomRightTestBoxRight = new ScaledPoint(bottomRightTestBoxLeft);
            ScaledPoint topLeftTestBoxUp = new ScaledPoint(topLeftTestBoxLeft);
            ScaledPoint bottomRightTestBoxUp = new ScaledPoint(bottomRightTestBoxLeft);
            ScaledPoint topLeftTestBoxDown = new ScaledPoint(topLeftTestBoxLeft);
            ScaledPoint bottomRightTestBoxDown = new ScaledPoint(bottomRightTestBoxLeft);
            while (true) {
                if (testBoxPositionIsValid(box, topLeftTestBoxLeft, bottomRightTestBoxLeft)) {
                    box.setPosition(topLeftTestBoxLeft);
                    return;
                }
                if (testBoxPositionIsValid(box, topLeftTestBoxRight, bottomRightTestBoxRight)) {
                    box.setPosition(topLeftTestBoxRight);
                    return;
                }
                if (testBoxPositionIsValid(box, topLeftTestBoxUp, bottomRightTestBoxUp)) {
                    box.setPosition(topLeftTestBoxUp);
                    return;
                }
                if (testBoxPositionIsValid(box, topLeftTestBoxDown, bottomRightTestBoxDown)) {
                    box.setPosition(topLeftTestBoxDown);
                    return;
                }
                topLeftTestBoxLeft = topLeftTestBoxLeft.move(Direction.LEFT.getDirection());
                bottomRightTestBoxLeft = bottomRightTestBoxLeft.move(Direction.LEFT.getDirection());
                topLeftTestBoxRight = topLeftTestBoxRight.move(Direction.RIGHT.getDirection());
                bottomRightTestBoxRight = bottomRightTestBoxRight.move(Direction.RIGHT.getDirection());
                topLeftTestBoxUp = topLeftTestBoxUp.move(Direction.UP.getDirection());
                bottomRightTestBoxUp = bottomRightTestBoxUp.move(Direction.UP.getDirection());
                topLeftTestBoxDown = topLeftTestBoxDown.move(Direction.DOWN.getDirection());
                bottomRightTestBoxDown = bottomRightTestBoxDown.move(Direction.DOWN.getDirection());
            }
        }
    }

    boolean boxPositionIsValid(Box box) {
        for (Box b : boxes) {
            if (b != box) {
                if (isOverlapping(getTopLeftWithPadding(box), getBottomRightWithPadding(box), getTopLeftWithPadding(b), getBottomRightWithPadding(b))) {
                    return false;
                }
            }
        }
        //todo: händer två ggr när man lägger till en box
        return true;
    }

    boolean testBoxPositionIsValid(Box box, ScaledPoint topLeftTest, ScaledPoint bottomRightTest) {
        for (Box b : boxes) {
            if (b != box) {
                if (isOverlapping(topLeftTest, bottomRightTest, getTopLeftWithPadding(b), getBottomRightWithPadding(b))) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean isOverlapping(ScaledPoint topLeft1, ScaledPoint bottomRight1, ScaledPoint topLeft2, ScaledPoint bottomRight2) {
        if (topLeft1.getY(Scale.Backend) > bottomRight2.getY(Scale.Backend)
                || bottomRight1.getY(Scale.Backend) < topLeft2.getY(Scale.Backend)) {
            return false;
        }
        if (topLeft1.getX(Scale.Backend) > bottomRight2.getX(Scale.Backend)
                || bottomRight1.getX(Scale.Backend) < topLeft2.getX(Scale.Backend)) {
            return false;
        }
        return true;
    }

    boolean contains(Box box, ScaledPoint point) {
        ScaledPoint topLeft = getTopLeftWithPadding(box);
        ScaledPoint bottomRight = getBottomRightWithPadding(box);

        if (topLeft.getX(Scale.Backend) < point.getX(Scale.Backend) && point.getX(Scale.Backend) < bottomRight.getX(Scale.Backend)) {
            if (topLeft.getY(Scale.Backend) < point.getY(Scale.Backend) && point.getY(Scale.Backend) < bottomRight.getY(Scale.Backend)) {
                return true;
            }
        }
        return false;
    }

    private ScaledPoint getTopLeft(Box box) {
        return box.getPosition();
    }

    private ScaledPoint getBottomRight(Box box) {
        return box.getPosition().move(box.getWidthAndHeight());
    }

    private ScaledPoint getTopLeftWithPadding(Box box) {
        return box.getPosition().move(Scale.Backend, -boxPadding, -boxPadding);
    }

    private ScaledPoint getBottomRightWithPadding(Box box) {
        return box.getPosition().move(box.getWidthAndHeight()).move(Scale.Backend, boxPadding, boxPadding);
    }
}