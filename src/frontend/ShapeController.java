package frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import model.Model;
import model.boxes.Diagram;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class ShapeController extends AnchorPane {

    @FXML
    private Button save_button;

    Model model = Model.getModel();
    CanvasController canvas;

    public ShapeController(CanvasController canvas) {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("view/shapes.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.canvas = canvas;
    }

    @FXML
    private void addBox(){
        model.addBox(canvas.getMiddle());
    }

    @FXML
    private void addTemplate(){
        //model.addTemplate(canvas.getMiddle());
    }

    @FXML
    private void saveTemplate(){
        //hämta template från en lista av sparade templates
    }

    @FXML
    private void minimize(){
        this.setVisible(false);
    }

}
