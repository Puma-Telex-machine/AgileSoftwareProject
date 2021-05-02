package model.point;

public class ScaledArea {
    private final ScaledPoint topLeft;
    private final ScaledPoint bottomRight;

    public ScaledArea(ScaledPoint topLeft, ScaledPoint bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    ScaledPoint getTopLeft() {
        return topLeft;
    }

    ScaledPoint getTopRight() {
        return new ScaledPoint(Scale.Internal, bottomRight.getX(Scale.Internal), topLeft.getY(Scale.Internal));
    }

    ScaledPoint getBottomRight() {
        return bottomRight;
    }

    ScaledPoint getBottomLeft() {
        return new ScaledPoint(Scale.Internal, topLeft.getX(Scale.Internal), bottomRight.getY(Scale.Internal));
    }
}
