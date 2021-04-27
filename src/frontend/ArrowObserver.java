package frontend;

import model.facades.BoxFacade;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface ArrowObserver {
    void arrowEvent(Point p, BoxController box);
}
