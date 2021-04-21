package viewmodel;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.io.IOException;


public class MethodArgumentEditorController {

    @FXML
    protected  TextField argumentTypeField;

    public MethodArgumentEditorController(VBox spawnBox)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("view/MethodArgumentEditor.fxml")));

        fxmlLoader.setRoot(spawnBox);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
