import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Left_menuController extends AnchorPane{

    @FXML
    AnchorPane base_menu, left_anchorpane;
    @FXML
    Button button1, button2, button3, button4, button5, button6, template1, new_button;
   
  public Left_menuController() {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("view/left_menu.fxml"));

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
        if(event.getSource() == button1) {
            //TODO button1.setBackground(); to #3E4046
        
        }
        else if (event.getSource() == button2) {
            //set color to #3E4046
        }
        else if (event.getSource() == button3) {
            //set color to #3E4046
        }
        else if (event.getSource() == button4) {
            //set color to #3E4046
        }
        else if (event.getSource() == button5) {
          //set color to #3E4046
        }
        else if (event.getSource() == button6) {
            //set color to #3E4046
        }
    }
}