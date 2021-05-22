package frontend.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.facades.FileHandlerFacade;

import java.io.IOException;

public class TemplatesController extends AnchorPane {

    @FXML
    VBox templatesBox;

    FileHandlerFacade fileHandler;

    CanvasController canvas;

    public TemplatesController(FileHandlerFacade fileHandler, CanvasController canvas) {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("../view/templates.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.fileHandler = fileHandler;
        this.canvas = canvas;
        String[] files = fileHandler.getAllTemplateNames();

        for(int i = 0; i < files.length; i++)
        {
            TemplateItemController file = new TemplateItemController(files[i], fileHandler, canvas);
            templatesBox.getChildren().add(file);
        }
    }

    public void updateItems(){
        templatesBox.getChildren().clear();
        String[] files = fileHandler.getAllTemplateNames();
        for(int i = 0; i < files.length; i++)
        {
            TemplateItemController file = new TemplateItemController(files[i], fileHandler, canvas);
            templatesBox.getChildren().add(file);
        }
    }
}
