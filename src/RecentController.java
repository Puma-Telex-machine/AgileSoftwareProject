import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import model.Model;

import java.io.IOException;

@FXML
Button minimize_button;

public class RecentController extends AnchorPane{

    Model model = Model.getModel();

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
    private void handleButtonAction(ActionEvent event){
        //todo add new
        //minimize_button 

    }
}