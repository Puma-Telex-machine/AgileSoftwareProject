package model.grid;

import java.awt.*;
import java.util.ArrayList;

public class Scaler {
    static int width = 30;
    static int height = 30;

    public static Point convertFromScale(Point point) {
        int convertedX = point.x * width;
        int convertedY = point.y * height;
        return new Point(convertedX, convertedY);
    }

    public static ArrayList<Point> convertFromScale(ArrayList<Point> points) {
        ArrayList<Point> convertedPoints = new ArrayList<>();
        for (Point p : points) {
            convertedPoints.add(convertFromScale(p));
        }
        return convertedPoints;
    }

    public static Point convertToScale(Point point) {
        int convertedX = point.x / width;
        int convertedY = point.y / height;
        return new Point(convertedX, convertedY);
    }

    public static ArrayList<Point> convertToScale(ArrayList<Point> points) {
        ArrayList<Point> convertedPoints = new ArrayList<>();
        for (Point p : points) {
            convertedPoints.add(convertToScale(p));
        }
        return convertedPoints;
    }
}
