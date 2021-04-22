package viewmodel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


/** Controller class for the left menu
 * @author Madeleine Xia
 * @version 1.0
 * @since 2021-04-xx
 * 
 */

public class Left_menuController {

    @FXML
    AnchorPane base_menu, top_anchorpane, middle_anchorpane, left_anchorpane;
    @FXML
    Button button1, button2, button3, button4, button5, button6, template1, new_button;
    @FXML 
    Pane pane1, pane2, pane3, pane4, pane5, pane6;
    @FXML
    TextField searchbar;
    @FXML
    Text uml_text, templates_text, recentworktext, textformattext, exercisetext, commenttext, overviewtext;
   

    BoxController boxC;

  /*public Left_menuController() {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("view/left_menu.fxml"));

        //fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }*/

    @FXML
    private void handleButtonAction(ActionEvent event){
        if(event.getSource() == button1) {
            pane1.toFront();
        }
        else if (event.getSource() == button2) {
            pane2.toFront();
            searchbar.toFront();
            uml_text.toFront();
            templates_text.toFront();
            
            //create box/add box knapp
            //templates ska upp
        }
        else if (event.getSource() == button3) {
            pane3.toFront();
            textformattext.toFront();

        }
        else if (event.getSource() == button4) {
            pane4.toFront();
            exercisetext.toFront();
        }
        else if (event.getSource() == button5) {
            pane5.toFront();
            commenttext.toFront();
        }
        else if (event.getSource() == button6) {
            pane6.toFront();
            overviewtext.toFront();
        }
        /*else if (event.getSource() == template1){
            (en fxml för template1).toFront();

        }*/
    }
}