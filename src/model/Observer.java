package model;

import java.awt.*;

public interface Observer {
    void updateAll();
    void updatePoint(Point point);
}
