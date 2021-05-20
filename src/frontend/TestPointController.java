package frontend;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class TestPointController extends AnchorPane {
    public TestPointController(float x, float y) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("view/TestPoint.fxml")));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.setLayoutX(x);
        this.setLayoutY(y);
    }
}
