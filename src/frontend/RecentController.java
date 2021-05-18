package frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import model.Model;

import java.io.IOException;

public class RecentController extends AnchorPane{

    Model model = Model.getModel();

    SaveAsController saveAs;
    BaseController base;

    public RecentController() {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("view/recent_work.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void openSave() {base.openSave();}

    @FXML
    private void minimize(){
        this.setVisible(false);
    }


}