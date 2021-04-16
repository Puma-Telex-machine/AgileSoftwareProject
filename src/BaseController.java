import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BaseController {

    @FXML
    private Label boxLabel;

    public BaseController(){

    }

    @FXML
    private void buttonPressed(Event e){
        //todo Notify backend
    }
    public void addBox(){
        //todo accually add box
        boxLabel.setText("box added");
    }
}
