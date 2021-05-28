package frontend.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import model.facades.ExerciseFacade;

import java.awt.*;
import java.io.IOException;

public class ExercisesMenuItemController extends AnchorPane {

    @FXML
    Label nameLable;

    private  ExerciseFacade exercise;

    public ExercisesMenuItemController(ExerciseFacade exercise) {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("..//view/ExercisesMenuItem.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        nameLable.setText(exercise.getName());
        this.exercise = exercise;
    }

    @FXML
    private void openExercise()
    {
        exercise.startExercise();
    }

}
