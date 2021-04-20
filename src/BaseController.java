import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Box;
import model.Model;
import model.Observer;

import java.awt.*;


public class BaseController implements Observer{

    @FXML
    private Label boxLabel;
    @FXML
    private Label context;
    @FXML
    private Label source;
    @FXML
    AnchorPane pane;

    Model model;

    BoxController box;

    public BaseController() {

        model = Model.getModel();
        model.addObserver(this);
    }

    @FXML
    private void buttonPressed(Event e) {
        model.addBox(new Point(0, 0));
    }

    @Override
    public void addBox(Box b){
        box = new BoxController(b);
        pane.getChildren().add(this.box);
        boxLabel.setText("box added");

    }
    @FXML
    public void contextmenu(){
        //todo add adding of boxes here
        context.setText("contextMenu opened");
    }

    @FXML
    public void handleDragLabel(MouseEvent event){
        source.setText("dragging");

        source.setLayoutX(source.getLayoutX()+event.getX()-25);
        source.setLayoutY(source.getLayoutY()+event.getY());
        event.consume();
    }
    public void handleDragOver(MouseEvent event){
        source.setText("Drag test");
        event.consume();
    }
}
