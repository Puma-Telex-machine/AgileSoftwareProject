package model.teststuff;

import model.boxes.Box;
import model.facades.BoxFacade;
import model.point.ScaledPoint;

import java.util.List;
import java.util.TreeMap;

public class Diagram2 {

    TreeMap<ScaledPoint, Box> boxMap = new TreeMap<>();

    public List<Box> getBoxes() {
        return null;
    }

    public boolean isEmpty(ScaledPoint scaledPoint) {
        return boxMap.containsKey(scaledPoint);
    }

    public BoxFacade createBox(Box box) {
        return null;
    }
}