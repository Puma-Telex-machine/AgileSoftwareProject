package model;

import java.awt.*;

public class Box {

    private String name;
    private Point position;
    private String type;

    public Box(Point position, String name, String type) {
        this.position = position;
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
