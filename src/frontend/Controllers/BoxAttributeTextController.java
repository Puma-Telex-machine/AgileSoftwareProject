package frontend.Controllers;

import javafx.fxml.FXML;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.io.IOException;

public class BoxAttributeTextController extends AnchorPane {
    @FXML
    Label nameLable;

    public BoxAttributeTextController(String text) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/BoxAttributeText.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        nameLable.setText(text);
    }
    public Font getFont(){
        return nameLable.getFont();
    }
}
