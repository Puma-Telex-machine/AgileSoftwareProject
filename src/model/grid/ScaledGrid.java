package model.grid;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class ScaledGrid<V> {

    private final Grid<V> grid = new Grid<>();

    final int gridHeight;
    final int gridWidth;

    public ScaledGrid(int gridWidth, int gridHeight) {
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
    }


    public boolean set(Point point, V value) {
        return grid.set(convertToScale(point), value);
    }

    public V get(Point point) {
        return grid.get(convertToScale(point));
    }

    public void remove(Point point) {
        grid.remove(convertToScale(point));
    }

    public void remove(ArrayList<Point> points) {
        for (Point p : points) {
            remove(convertToScale(p));
        }
    }

    public boolean move(Point from, Point to) {
        return grid.move(convertToScale(from), convertToScale(to));
    }

    public boolean shift(ArrayList<Point> points, Point shiftDistance) {
        return grid.shift(convertToScale(points), convertToScale(shiftDistance));
    }

    public boolean isEmpty(Point point) {
        return grid.isEmpty(convertToScale(point));
    }

    public ArrayList<V> getAllEntries(){
        ArrayList<V> result = new ArrayList<>();
        for (Map.Entry<Point, V> entry: grid.grid.entrySet()) {
            result.add(entry.getValue());
        }
        return result;
    }

    private Point convertToScale(Point point) {
        int convertedX = point.x / gridWidth;
        int convertedY = point.y / gridHeight;
        return new Point(convertedX, convertedY);
    }

    private ArrayList<Point> convertToScale(ArrayList<Point> points) {
        ArrayList<Point> convertedPoints = new ArrayList<>();
        for (Point p : points) {
            convertedPoints.add(convertToScale(p));
        }
        return convertedPoints;
    }
}