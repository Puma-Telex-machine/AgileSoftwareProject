package model.boxes;

import model.grid.BoxGrid;
import model.grid.ScaledGrid;
import model.grid.VirtualBox;
import model.relations.Relation;

import java.awt.*;
import java.util.*;

public class Diagram {

    BoxGrid boxGrid = new BoxGrid(6, 8);
    ScaledGrid<Box> connectionGrid = new ScaledGrid<>(3, 4);
    HashSet<Relation> relations = new HashSet<>();

    public Box createBox(Point position) {
        return boxGrid.createBox(position);
    }

    public void remove(Box box) {
        boxGrid.removeBox(box.getPosition());
    }
    public boolean move(Box box, Point position) {
        return boxGrid.moveBox(box.getPosition(), position);
    }
}
