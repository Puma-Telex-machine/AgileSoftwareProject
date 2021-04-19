package viewmodel;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Box;
import model.Model;
import model.Observer;

import java.awt.*;

public class BaseController implements Observer{

    @FXML
    private Label boxLabel;
    @FXML
    private Label context;

    Model model;

    public BaseController() {
        model = Model.getModel();
        model.addObserver(this);
    }

    @FXML
    private void buttonPressed(Event e) {
        model.addBox(new Point(0, 0));
    }

    @Override
    public void addBox(Box box){
        //todo accually add box
        boxLabel.setText("box added");
    }
    @FXML
    public void contextmenu(){
        //todo add adding of boxes here
        context.setText("contextMenu opened");
    }
}
