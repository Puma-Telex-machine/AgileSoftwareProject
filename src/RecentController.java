import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.Model;

import java.awt.*;
import java.io.IOException;


/** Controller class for the left menu
 * @author Madeleine Xia
 * @version 1.0
 * @since 2021-04-xx
 *
 */

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
    }
}