import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import model.Model;

import java.awt.*;
import java.io.IOException;

public class ShapeController extends AnchorPane {

    Model model = Model.getModel();

    public ShapeController() {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("view/shapes.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void addBox(){
        model.addBox(new Point(500,200));
    }

}
