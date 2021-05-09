package frontend.Observers;
import frontend.BoxController;
import model.point.ScaledPoint;

import java.awt.*;

public interface ArrowObserver {
    void arrowEvent(ScaledPoint p, BoxController box);
}
