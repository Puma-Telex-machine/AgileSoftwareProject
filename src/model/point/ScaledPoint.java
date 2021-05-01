package model.point;


public class ScaledPoint implements Comparable<ScaledPoint> {
    private int x;
    private int y;

    public ScaledPoint(Scale scale, ScaledPoint point) {
        setX(scale, point.getX(scale));
        setY(scale, point.getY(scale));
    }

    public ScaledPoint(Scale scale, int x, int y) {
        setX(scale, x);
        setY(scale, y);
    }

    public int getX(Scale scale) {
        return x * scale.xScale;
    }

    public void setX(Scale scale, int x) {
        this.x = x / scale.xScale;
    }

    public int getY(Scale scale) {
        return y * scale.yScale;
    }

    public void setY(Scale scale, int y) {
        this.y = y / scale.yScale;
    }

    public ScaledPoint move(ScaledPoint point, ScaledPoint movement) {
        int movedX = point.x + movement.x;
        int movedY = point.y + movement.y;
        return new ScaledPoint(Scale.Internal, movedX, movedY);
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
