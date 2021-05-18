package frontend;

import frontend.CanvasController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SaveAsController extends AnchorPane {

    public SaveAsController() {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("view/saveAs.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void minimize(){
        this.setVisible(false);
    }
}

