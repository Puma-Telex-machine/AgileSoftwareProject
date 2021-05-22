package frontend.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.facades.FileHandlerFacade;


import java.io.IOException;

public class saveAsController extends AnchorPane {

    @FXML
    private CheckBox asTemplate;
    @FXML
    private TextField name;

    FileHandlerFacade fileHandler;
    public saveAsController(FileHandlerFacade fileHandler){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("../view/saveAs.fxml")));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.fileHandler=fileHandler;
    }

    @FXML
    private void save(){
        String fileName = name.getText();
        //todo save as fileName
        minimize();
    }

    @FXML
    private void minimize(){
        this.setVisible(false);
        this.toBack();
    }

}
