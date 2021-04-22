import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Box;
import model.Model;
import model.Observer;

import java.awt.*;


public class BaseController implements Observer{

    @FXML
    private VBox context;

    @FXML
    AnchorPane UML;
    @FXML
    AnchorPane contextMenu;

    Model model;

    public BaseController() {

        model = Model.getModel();
        model.addObserver(this);
    }

    @FXML
    private void handleAddBox(Event e) {
        model.addBox(new Point(0, 0));
        e.consume();
    }

    @Override
    public void addBox(Box b){
        BoxController box = new BoxController(b);
        UML.getChildren().add(box);
    }
    @FXML
    public void handleContextMenu(ContextMenuEvent e){
        context.setLayoutX(e.getX());
        context.setLayoutY(e.getY());
        contextMenu.toFront();
        e.consume();
    }
    @FXML
    public void exitContext(){
        UML.toFront();
    }
    @FXML
    private void handleContextAddBox(MouseEvent e) {
        model.addBox(new Point((int) context.getLayoutX()-80,(int) context.getLayoutY()-35));
        exitContext();
        e.consume();
    }
}
