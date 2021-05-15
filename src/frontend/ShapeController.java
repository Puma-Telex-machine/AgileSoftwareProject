package frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import model.Model;
import model.boxes.BoxType;
import model.point.Scale;
import model.point.ScaledPoint;

import java.io.IOException;

public class ShapeController extends AnchorPane {

    Model model = Model.getModel();
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
        model.addBox(new ScaledPoint(Scale.Frontend, canvas.getMiddle().x, canvas.getMiddle().y), BoxType.CLASS);
    }
    @FXML
    private void addInterface(){
        model.addBox(new ScaledPoint(Scale.Frontend, canvas.getMiddle().x, canvas.getMiddle().y), BoxType.INTERFACE);
    }
    @FXML
    private void addAbstract(){
        model.addBox(new ScaledPoint(Scale.Frontend, canvas.getMiddle().x, canvas.getMiddle().y), BoxType.ABSTRACT_CLASS);
    }
    @FXML
    private void addEnum(){
        model.addBox(new ScaledPoint(Scale.Frontend, canvas.getMiddle().x, canvas.getMiddle().y), BoxType.ENUM);
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
