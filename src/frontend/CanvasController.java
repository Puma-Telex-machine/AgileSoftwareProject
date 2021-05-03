package frontend;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import model.facades.Observer;
import model.facades.BoxFacade;
import model.facades.RelationFacade;
import model.point.Scale;
import model.point.ScaledPoint;

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

    @Override
    public void addRelation(RelationFacade relation) {
        
    }

    public ScaledPoint getMiddle(){
        return new ScaledPoint(Scale.Frontend,500, 400);
    }

}
