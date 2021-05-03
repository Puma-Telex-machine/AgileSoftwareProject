package frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class OverviewController extends AnchorPane {
    public OverviewController() {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("view/Overview.fxml"));

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
        this.toBack();
        this.setVisible(false);
    }
}
