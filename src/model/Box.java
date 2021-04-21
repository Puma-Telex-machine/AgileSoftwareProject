package model;

import java.awt.*;

public class Box {

    private String name;
    private Point position;

    public Box(Point position, String name) {
        this.position = position;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
