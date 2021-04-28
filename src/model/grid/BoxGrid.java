package model.grid;

import model.grid.boxes.Box;

import java.awt.*;

public class BoxGrid {

    private final ScaledGrid<Box> grid;
    private int boxCounter = 0; //Needs to be saved if we want to use it

    public BoxGrid(int gridWidth, int gridHeight) {
        grid = new ScaledGrid<>(gridWidth, gridHeight);
    }

    public Box createBox(Point position) {
        String boxName = "Box " + boxCounter;
        Box box = new Box(boxName, position);

        if (grid.set(position, box)) {
            boxCounter++;
            return box;
        }
        return null;
    }

    public Point moveBox(Box from, Point to) {
        return grid.move(from.getPosition(), to);
    }

    public void removeBox(Point position) {
        grid.remove(position);
    }

}
