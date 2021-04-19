import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BaseController {

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
    public void addBox(){
        //todo accually add box
        boxLabel.setText("box added");
    }
    public void contextmenu(){
        //todo add adding of boxes here
        context.setText("contextMenu opened");
    }
}
