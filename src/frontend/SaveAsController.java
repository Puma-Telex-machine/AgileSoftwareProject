package frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Model;
import model.ModelFacade;
import model.facades.FileHandlerFacade;

import java.io.IOException;

public class SaveAsController extends AnchorPane {

    @FXML
    CheckBox templateCheck;

    @FXML
    TextField nameField;

    FileHandlerFacade fileHandler = ModelFacade.getFileHandler();

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

    @FXML
    private void handleSaving(){
        if(templateCheck.isSelected()){
            fileHandler.saveTemplate(nameField.getText());
        }else{
            fileHandler.saveAs(nameField.getText());
        }
        minimize();
    }
}
