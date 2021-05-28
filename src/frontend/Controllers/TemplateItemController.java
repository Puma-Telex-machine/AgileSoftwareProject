package frontend.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.facades.FileHandlerFacade;

import java.io.IOException;

public class TemplateItemController extends AnchorPane {

    @FXML
    Label nameLable;

    String fileName = "";

    FileHandlerFacade fileHandler;

    CanvasController canvas;

    public TemplateItemController(String fileName, FileHandlerFacade fileHandler, CanvasController canvas) {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/view/TemplateMenuItem.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.fileName = fileName;
        nameLable.setText(fileName);
        this.fileHandler = fileHandler;
        this.canvas = canvas;
    }

    @FXML
    private void openTemplate()
    {
        fileHandler.loadTemplate(fileName);
    }

    @FXML
    private void deleteItem(){
        fileHandler.deleteTemplate(fileName);
        this.setVisible(false);
    }
}
