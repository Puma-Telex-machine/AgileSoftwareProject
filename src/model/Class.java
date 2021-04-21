package model;

import java.awt.*;
import java.util.ArrayList;

public class Class extends Box {

    ArrayList<Method> methods = new ArrayList<>();

    public Class(Point position, String name) {
        super(position, name);
    }

    @Override
    public String getType(){
        return "Class";
    }
}
