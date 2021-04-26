package model.grid;

import model.grid.boxes.Box;
import model.grid.relations.Relation;

import java.awt.*;

public class RelationGrid {

    private final ScaledGrid<Relation> grid;

    public RelationGrid(int gridWidth, int gridHeight) {
        grid = new ScaledGrid<>(gridWidth, gridHeight);
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

    public Point moveBox(Box from, Point to) {
        return boxGrid.move(from.getPosition(), to);
    }

    public void removeBox(Point position) {
        boxGrid.remove(position);
    }
}
