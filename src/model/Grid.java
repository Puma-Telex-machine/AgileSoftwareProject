package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.TreeMap;

public class Grid<V> {

    TreeMap<Point, V> grid = new TreeMap<>((p1, p2) -> {
        if (p1.x < p2.x) return -1;
        else if (p1.x > p2.x) return 1;
        else {
            return Integer.compare(p1.y, p2.y);
        }
    });

    public boolean set(Point point, V value) {
        if (isOccupied(point)){
            return false;
        }
        grid.put(point, value);
        return true;
    }

    public V get(Point point) {
        return grid.get(point);
    }

    public void remove(Point point) {
        grid.remove(point);
    }

    public void remove(ArrayList<Point> points) {
        for (Point p : points) {
            remove(p);
        }
    }

    public boolean move(Point from, Point to) {
        if (isOccupied(to)){
            return false;
        }
        grid.put(to, get(from));
        grid.remove(from);
        return true;
    }

 /*
    public boolean shift(Point point, Point shift) {
        Point toPoint = new Point(point.x + shift.x, point.y + shift.y);
        if (isOccupied(newPoint)) {
            return false;
        }
        grid.put(newPoint, get(point));
        grid.remove(point);
        return true;
    }
 */

    public boolean shift(ArrayList<Point> points, Point shift) {
        ArrayList<Point> toPoints = new ArrayList<>();

        // Fill array with shifted points
        for (Point p : points) {
            toPoints.add(new Point(new Point(p.x + shift.x, p.y + shift.y)));
        }
        // Check if move should be possible
        for (Point p : toPoints) {
            if (points.contains(p)) continue;       // Ignore points to be moved
            if (isOccupied(p)) return false;        // If no new point is occupied, continue
        }
        // Move each point
        for (int i = 0; i < points.size(); i++) {
            Point fromPoint = points.get(i);
            grid.put(toPoints.get(i), get(fromPoint));
            grid.remove(fromPoint);
        }
        return true;
    }

    private boolean isOccupied(Point point) {
        boolean isOccupied = grid.containsKey(point);
        if (isOccupied) System.out.println("Position already is occupied.");
        return isOccupied;
    }
}
