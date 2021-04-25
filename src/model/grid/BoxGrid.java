package model.grid;

import model.boxes.Box;

import java.awt.*;

public class BoxGrid {

    private final ScaledGrid<Box> boxGrid;
    private int boxCounter = 0; //Needs to be saved somehow

    public BoxGrid(int gridWidth, int gridHeight) {
        boxGrid = new ScaledGrid<>(gridWidth, gridHeight);
    }

    public Box createBox(Point position) {
        String boxName = "Box " + boxCounter;
        Box box = new Box(boxName, position);

        if (boxGrid.set(position, box)) {
            boxCounter++;
            return box;
        }
        return null;
    }

    public boolean moveBox(Point from, Point to) {
        return boxGrid.move(from, to);
    }

    public void removeBox(Point position) {
        boxGrid.remove(position);
    }
}
