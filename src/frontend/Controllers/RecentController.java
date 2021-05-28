package frontend.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Model;
import model.facades.FileHandlerFacade;

import java.io.IOException;

public class RecentController extends AnchorPane{

    @FXML
    VBox filesBox;

    FileHandlerFacade fileHandler;

    SaveAsController saveAs;

    public RecentController(FileHandlerFacade fileHandler) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/recent_work.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        saveAs = new SaveAsController();

        this.getChildren().add(saveAs);

        saveAs.setLayoutX(400);
        saveAs.setVisible(false);
        saveAs.toBack();

        /*this.fileHandler = fileHandler;
        String[] files = fileHandler.getAllFileNames();

        for(int i = 0; i < files.length; i++)
        {
            FileMenuItemController file = new FileMenuItemController(files[i], fileHandler, canvas);
            filesBox.getChildren().add(file);
        }*/
    }

    @FXML
    private void openSave()
    {
        saveAs.setVisible(true);
        saveAs.toFront();
    }
}