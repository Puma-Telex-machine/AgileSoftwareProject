package frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;


public class MethodArgumentEditorController extends AnchorPane {

    @FXML
    protected TextField argumentTypeField;

    @FXML
    protected Pane highlightPane;

    @FXML
    protected Label paramLable;

    public MethodArgumentEditorController()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("view/MethodArgumentEditor.fxml")));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
