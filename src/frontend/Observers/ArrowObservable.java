package frontend.Observers;


import javafx.scene.input.MouseEvent;
import model.facades.BoxFacade;

import java.awt.*;

public interface ArrowObservable {
    void notifyArrowEvent(MouseEvent e);
    void notifyBoxDrag();
}
