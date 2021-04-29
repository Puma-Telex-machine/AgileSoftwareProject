package frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExercisesController extends AnchorPane {

    public ExercisesController() {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("view/Exercises.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    VBox leftBox;

    @FXML
    VBox rightBox;

    private List<ExercisesMenuItemController> exercises = new ArrayList<ExercisesMenuItemController>(0);

    private void displayExercises ()
    {
        
    }
}
