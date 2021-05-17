package model.diagram;

import global.point.Scale;
import global.point.ScaledPoint;

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final ScaledPoint dir;

    Direction(int x, int y) {
        this.dir = new ScaledPoint(Scale.Backend, x, y);
    }

    public int getX() {
        return dir.getX(Scale.Backend);
    }

    public int getY() {
        return dir.getY(Scale.Backend);
    }

    public ScaledPoint getDirection() {
        return dir;
    }
}
