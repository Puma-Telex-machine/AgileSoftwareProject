package model.point;


import java.awt.*;

public class ScaledPoint implements Comparable<ScaledPoint> {
    private int x;
    private int y;

    public ScaledPoint(Scale scale, ScaledPoint point) {
        setX(scale, point.getX(scale));
        setY(scale, point.getY(scale));
    }

    public ScaledPoint(Scale scale, Point point) { // This constructor can be removed when (if) all uses of Point is removed
        setX(scale, point.x);
        setY(scale, point.y);
    }

    public ScaledPoint(Scale scale, int x, int y) {
        setX(scale, x);
        setY(scale, y);
    }

    public ScaledPoint(Scale scale, double x, double y) {
        setX(scale, (int) x);
        setY(scale, (int) y);
    }

    private void setX(Scale scale, int x) {
        this.x = x / scale.xScale;
    }

    public int getX(Scale scale) {
        return x * scale.xScale;
    }

    private void setY(Scale scale, int y) {
        this.y = y / scale.yScale;
    }

    public int getY(Scale scale) {
        return y * scale.yScale;
    }

    public ScaledPoint move(ScaledPoint direction) {
        int movedX = x + direction.x;
        int movedY = y + direction.y;
        return new ScaledPoint(Scale.Internal, movedX, movedY);
    }

    public ScaledPoint move(Scale scale, int dx, int dy) {
        return move(new ScaledPoint(scale, dx, dy));
    }

    @Override
    public int compareTo(ScaledPoint point) {
        if (x < point.x) return -1;
        else if (x > point.x) return 1;
        else {
            return Integer.compare(y, point.y);
        }
    }
}
