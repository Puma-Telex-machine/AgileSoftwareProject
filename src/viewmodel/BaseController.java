package viewmodel;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Box;
import model.Model;
import model.Observer;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

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
        boxLabel.setText("box added");
    }
}
