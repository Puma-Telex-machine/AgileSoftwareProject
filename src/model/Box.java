package model;

import java.awt.*;

public class Box {
    Point position;
    public Box(Point position) {
        this.position=position;
    }
    public Point getPosition(){
        return position;
    }
    public void setPosition(Point position){
        this.position=position;
    }
}
