package model.point;

public class ScaledArea {
    private ScaledPoint topLeft;
    private ScaledPoint bottomRight;


    ScaledPoint[] getFullArea(Scale scale) {

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
