package frontend.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.facades.ExerciseFacade;
import model.facades.ExercisesHandlerFacade;

import java.io.IOException;

public class ExercisesController extends AnchorPane {

    public ExercisesController() {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("..//view/Exercises.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        //exercises = exercisesHandler.getAllExercises();
        /*
        for (int i = 0; i < exercises.length; i++) {
            ExerciseFacade exercise = exercises[i];

            ExercisesMenuItemController exerciseMenuItem = new ExercisesMenuItemController(exercise);

            boolean left = (i % 2 == 0);
            if (left) leftBox.getChildren().add(exerciseMenuItem);
            else rightBox.getChildren().add(exerciseMenuItem);
        }*/
    }

    @FXML
    private void minimize(){
        this.toBack();
        this.setVisible(false);
    }

    @FXML
    private VBox leftBox;

    @FXML
    private VBox rightBox;

    private ExercisesHandlerFacade exercisesHandler;

    private ExerciseFacade[] exercises;
    private ExercisesMenuItemController[] exerciseMenuItems;
}
