package model.point;

import java.awt.*;

public class ScaledArea {
    final ScaledPoint topLeft;
    final ScaledPoint bottomRight;
    final ScaledPoint topRight;
    final ScaledPoint bottomLeft;

    public ScaledArea(ScaledPoint topLeft, ScaledPoint bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.topRight = new ScaledPoint(bottomRight.x, topLeft.y);
        this.bottomLeft = new ScaledPoint(topLeft.x, bottomRight.y);
    }

    public boolean intersects(ScaledArea scaledArea) {
        return false;
    }

    public boolean contains(ScaledPoint scaledPoint) {
        int pointX = scaledPoint.x;
        int pointY = scaledPoint.y;

        int xMin = topLeft.x;
        int xMax = bottomRight.x;

        int yMin = bottomRight.y;
        int yMax = topLeft.y;

        return pointX > xMin && pointX < xMax && pointY > yMin && pointY < yMax;
    }

    public ScaledPoint getTopLeft() {
        return topLeft;
    }

    public ScaledPoint getBottomRight() {
        return bottomRight;
    }
}
