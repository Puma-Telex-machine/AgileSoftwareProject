package frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import model.Model;
import model.ModelFacade;
import model.boxes.BoxType;
import global.point.Scale;
import global.point.ScaledPoint;
import model.diagram.DiagramFacade;

import java.io.IOException;

public class ShapeController extends AnchorPane {

    DiagramFacade diagram = Model.getModel().getDiagram();
    CanvasController canvas;

    public ShapeController(CanvasController canvas) {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("view/shapes.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.canvas = canvas;
    }

    @FXML
    private void addClass(){
        diagram.createBox(new ScaledPoint(Scale.Frontend, canvas.getMiddle().x, canvas.getMiddle().y), BoxType.CLASS);
    }
    @FXML
    private void addInterface(){
        diagram.createBox(new ScaledPoint(Scale.Frontend, canvas.getMiddle().x, canvas.getMiddle().y), BoxType.INTERFACE);
    }
    @FXML
    private void addAbstract(){
        diagram.createBox(new ScaledPoint(Scale.Frontend, canvas.getMiddle().x, canvas.getMiddle().y), BoxType.ABSTRACT_CLASS);
    }
    @FXML
    private void addEnum(){
        diagram.createBox(new ScaledPoint(Scale.Frontend, canvas.getMiddle().x, canvas.getMiddle().y), BoxType.ENUM);
    }

    @FXML
    private void addTemplate(){
        //model.addTemplate(canvas.getMiddle());
    }

    @FXML
    private void saveTemplate(){
        //hämta template från en lista av sparade templates
    }

    @FXML
    private void minimize(){
        this.toBack();
        this.setVisible(false);
    }

}
