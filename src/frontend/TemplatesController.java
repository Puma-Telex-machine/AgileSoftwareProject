package frontend;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class TemplatesController extends AnchorPane {

    public TemplatesController() {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("view/templates.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
