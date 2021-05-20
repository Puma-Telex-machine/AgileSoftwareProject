package frontend.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SelectionBoxController extends AnchorPane {
    private double mouseDownX;
    private double mouseDownY;

    public SelectionBoxController()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("..//view/SelectionBox.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void startSelection (double x, double y)
    {
    }
}
