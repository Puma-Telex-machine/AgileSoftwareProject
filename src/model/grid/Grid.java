package model.grid;

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
        if (!isEmpty(point)){
            return false;
        }
        grid.put(point, value);
        return true;
    }
/*
    public boolean set(ArrayList<Point> points, V value) {
        for (Point p : points) {
            if (isOccupied(p)) return false;
        }
        for (Point p : points) {
            grid.put(p, value);
        }
        return true;
    }
 */

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

    public Point move(Point from, Point to) {
        if (!isEmpty(to)){
            return from;
        }
        grid.put(to, get(from));
        grid.remove(from);
        return to;
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

    public boolean shift(ArrayList<Point> points, Point shiftDistance) {
        ArrayList<Point> toPoints = new ArrayList<>();

        // Fill array with shifted points
        for (Point p : points) {
            toPoints.add(new Point(new Point(p.x + shiftDistance.x, p.y + shiftDistance.y)));
        }
        // Check if move should be possible
        for (Point p : toPoints) {
            if (points.contains(p)) continue;       // Ignore points to be moved
            if (!isEmpty(p)) return false;          // If no new point is occupied, continue
        }
        // Move each point
        for (int i = 0; i < points.size(); i++) {
            Point fromPoint = points.get(i);
            grid.put(toPoints.get(i), get(fromPoint));
            grid.remove(fromPoint);
        }
        return true;
    }

    public boolean isEmpty(Point point) {
        boolean isOccupied = grid.containsKey(point);
        if (isOccupied) System.out.println("Position already is occupied.");
        return !isOccupied;
    }
}