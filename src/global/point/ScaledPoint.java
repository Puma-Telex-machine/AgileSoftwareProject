package global.point;


import java.awt.*;

public class ScaledPoint implements Comparable<ScaledPoint> {
    int x;
    int y;

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

    ScaledPoint(int x, int y) {
        this.x = x;
        this.y = y;
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
        return new ScaledPoint(movedX, movedY);
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

    public boolean equals(Object o) {
        if (o instanceof ScaledPoint p) {
            return x == p.x && y == p.y;
        }
        return false;
    }
    public Point getPoint(Scale scale){
        return new Point(getX(scale),getY(scale));
    }
}
