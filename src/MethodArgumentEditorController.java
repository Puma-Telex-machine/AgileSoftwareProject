import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.io.IOException;


public class MethodArgumentEditorController extends AnchorPane {

    @FXML
    protected TextField argumentTypeField;

    @FXML
    protected Pane highlightPane;

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
