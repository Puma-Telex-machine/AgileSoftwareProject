package model.pathfinder;

import java.awt.*;

public interface GridView {

    int getMoveCost(Point point);

    int getEstimatedCost(Point from, Point to);

    boolean isOccupied(Point point);
}
