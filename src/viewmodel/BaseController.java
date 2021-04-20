package viewmodel;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
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
    private Label target;

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
    @FXML
    public void handleDragStart(MouseEvent event){
        source.setText("dragging");
        source.setLayoutX(event.getX()-25);
        source.setLayoutY(event.getY());
        event.consume();
    }
    public void dragOver(DragEvent dragEvent){
        source.setText("Drag done");
        source.setLayoutX(dragEvent.getX());
        source.setLayoutY(dragEvent.getY());
        dragEvent.consume();
    }
}
