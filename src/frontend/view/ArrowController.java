package frontend.view;

import frontend.ArrowObserver;
import frontend.MethodEditorController;
import frontend.VariableEditorController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import model.facades.BoxFacade;

import java.io.IOException;


public class ArrowController extends AnchorPane {
    public ArrowController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("view/Arrow.fxml")));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


}
