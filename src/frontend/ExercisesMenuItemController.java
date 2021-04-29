package frontend;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ExercisesMenuItemController extends AnchorPane {
    public ExercisesMenuItemController() {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("view/ExercisesMenuItem.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
