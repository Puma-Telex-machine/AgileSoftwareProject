package viewmodel;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.boxes.Box;
import model.Model;
import model.Observer;

import java.awt.*;

public class BaseController implements Observer {

    Model model;
    public BaseController() {
        model = Model.getModel();
        model.addObserver(this);
    }

    @FXML
    private Label boxLabel;

    @FXML
    private void buttonPressed(Event e) {
        model.addBox(new Point(0, 0));
        //todo Notify backend
    }

    public void addBox(Box box) {
        //todo actually add box
        boxLabel.setText(box.getName());
    }
}
