package frontend.Observers;
import frontend.BoxController;
import model.facades.BoxFacade;

import java.awt.*;

public interface ArrowObserver {
    void arrowEvent(Point p, BoxController box);
}
