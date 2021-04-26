package frontend;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import model.Observer;
import model.facades.BoxFacade;

import java.awt.*;
import java.io.IOException;

public class CanvasController extends AnchorPane implements Observer {

    VariableEditorController variableEditor;
    MethodEditorController methodEditor;
    public CanvasController()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("view/Canvas.fxml")));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        variableEditor = new VariableEditorController();
        methodEditor = new MethodEditorController();
        this.getChildren().add(methodEditor);
        this.getChildren().add(variableEditor);
        variableEditor.setVisible(false);
        methodEditor.setVisible(false);
    }

    @Override
    public void addBox(BoxFacade b){
        BoxController box = new BoxController(b,variableEditor,methodEditor);
        this.getChildren().add(box);
    }

    public Point getMiddle(){
        return new Point(500,400);
    }

}
