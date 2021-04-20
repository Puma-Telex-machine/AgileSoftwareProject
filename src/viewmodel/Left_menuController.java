package viewmodel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import model.Box;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

public class Left_menuController {

    @FXML AnchorPane base_menu;
    @FXML AnchorPane top_anchorpane;
    @FXML AnchorPane middle_anchorpane;
    @FXML AnchorPane left_anchorpane;

    @FXML Button button1;
    @FXML Button button2;
    @FXML Button button3;
    @FXML Button button4;
    @FXML Button button5;
    @FXML Button button6;

    @FXML Icon icon1;
    @FXML Icon icon2;
    @FXML Icon icon3;
    @FXML Icon icon4;
    @FXML Icon icon5;
    @FXML Icon icon6;

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Left_menu.fxml"));
    //fxmlLoader.setRoot(this);
    //fxmlLoader.setController(this);

    try {
        fxmlLoader.load();
    } catch (IOException exception) {
        throw new RuntimeException(exception);
    }

    
}
