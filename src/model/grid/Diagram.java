package model.grid;

import model.facades.BoxFacade;
import model.point.ScaledPoint;

public class Diagram {

    BoxGrid boxGrid = new BoxGrid();
    RelationGrid relationGrid = new RelationGrid();
    AStar aStar = new AStar(boxGrid, relationGrid);

    public BoxFacade createBox(ScaledPoint position) {
        return null;
    }
}