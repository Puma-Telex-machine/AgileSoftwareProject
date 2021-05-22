package frontend.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Model;
import model.ModelFacade;
import model.facades.FileHandlerFacade;

import java.io.IOException;

public class FilesController extends AnchorPane{

    @FXML
    VBox filesBox;

    SaveAsController saving = new SaveAsController();

    ModelFacade model = Model.getModel();

    FileHandlerFacade fileHandler;

    public FilesController(FileHandlerFacade fileHandler, CanvasController canvas) {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("..//view/Files.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.fileHandler = fileHandler;
        String[] files = fileHandler.getAllFileNames();

        for(int i = 0; i < files.length; i++)
        {
            FileMenuItemController file = new FileMenuItemController(files[i], fileHandler, canvas);
            filesBox.getChildren().add(file);
        }
        filesBox.getChildren().add(saving);
        saving.setVisible(false);
    }

    @FXML
    private void newFile()
    {
        fileHandler.newFile();
    }

    @FXML
    private void openSave(){saving.setVisible(true);}
}