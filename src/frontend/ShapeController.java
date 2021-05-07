package frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import model.Model;
import model.boxes.BoxType;

import java.io.IOException;

public class ShapeController extends AnchorPane {

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
        model.addBox(canvas.getMiddle(), BoxType.BOX);
    }

    @FXML
    private void minimize(){
        this.toBack();
        this.setVisible(false);
    }

}
