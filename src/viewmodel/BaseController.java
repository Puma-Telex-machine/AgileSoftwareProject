package viewmodel;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Box;
import model.Observer;

public class BaseController implements Observer{

    @FXML
    private Label boxLabel;
    @FXML
    private Label context;

    public BaseController(){

    }

    @FXML
    private void buttonPressed(Event e){
        //todo Notify backend
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
