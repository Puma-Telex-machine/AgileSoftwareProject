package model.grid;

import model.boxes.Box;
import model.point.Scale;
import model.point.ScaledPoint;


import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class BoxGrid implements IBoxGrid, BoxGridView {
    TreeMap<ScaledPoint, Box> boxMap = new TreeMap<>();

    public List<Box> getBoxes() {
        return null;
    }

    @Override
    public boolean isOccupied(ScaledPoint scaledPoint) {
        return boxMap.containsKey(scaledPoint);
    }

    public void add(Box box) {
        ArrayList<ScaledPoint> area = getArea(box);

        // Move other boxes in the area down and add the box area to the grid
        for (ScaledPoint p : area) {
            pushOthersDown(p);
            boxMap.put(p, box);
        }
    }

    @Override
    public void move(Box box, ScaledPoint point) {
        // Remove the old area of the box
        remove(box);

        // Update the position of the box
        box.setPosition(point);

        // Re-add the box to the grid
        add(box);
    }

    @Override
    public void remove(Box box) {
        ArrayList<ScaledPoint> area = getArea(box);

        // Remove the area from the grid
        for (ScaledPoint p : area) {
            boxMap.remove(p);
        }
    }

    public ArrayList<Box> getAllBoxes(){
        Set<Map.Entry<ScaledPoint, Box>> set = boxMap.entrySet();
        ArrayList<Box> boxes = new ArrayList<>();
        for (Map.Entry<ScaledPoint, Box> entry: set) {
            boxes.add(entry.getValue());
        }
        return boxes;
    }

    public int getBoxCounter(){
        return boxMap.size();
    }

    @Override
    public void update(Box box) {

    }

    private void pushOthersDown(ScaledPoint point) {
        //This is inefficient and could be run less times by calculating how far it needs to be moved instead
        Box occupant = boxMap.get(point);
        if (occupant != null) {
            ScaledPoint oldPosition = new ScaledPoint(Scale.Backend, occupant.getPosition());
            ScaledPoint newPosition = oldPosition.move(new ScaledPoint(Scale.Backend, 0, -1));
            move(occupant, newPosition);
        }
    }

    private ArrayList<ScaledPoint> getArea(Box box) {
        ArrayList<ScaledPoint> area = new ArrayList<>();
        ScaledPoint point = box.getPosition();
        int x = point.getX(Scale.Backend);
        int y = point.getY(Scale.Backend);
        int xEnd = x + box.getHeight();  // NOTE: Method getWidth does not exist!
        int yEnd = y + box.getHeight();

        for (; x < xEnd; x++) {
            for (; y < yEnd; y++) {
                area.add(new ScaledPoint(Scale.Backend, x, y));
            }
        }
        return area;
    }
}

