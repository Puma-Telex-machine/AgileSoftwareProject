package model.point;

public enum Scale {
    Internal(1, 1),
    Backend(1, 1),
    Frontend(30, 30);

    public final int xScale;
    public final int yScale;

    Scale(int xScale, int yScale) {
        this.xScale = xScale;
        this.yScale = yScale;
    }
}