package frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Model;
import model.boxes.BoxType;
import model.boxes.Interface;
import model.facades.BoxFacade;

import javax.swing.text.html.ImageView;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class ShapeController extends AnchorPane {

    @FXML
    AnchorPane UML;

    Model model = Model.getModel();
    CanvasController canvas;
    BaseController base;
    SaveAsController saveAs;

    public ShapeController(CanvasController canvas) {
        //base = new BaseController();

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

 /*private void init(){
        UML.getChildren().add(saveAs);
        saveAs.setVisible(true);
        UML.getChildren().add(canvas);
    }     */

    @FXML
    private void addBox(){model.addBox(canvas.getMiddle());}

    @FXML
    private void minimize(){
        this.setVisible(false);
    }

    @FXML
    private void openSave() {base.openSave();}


    /*@FXML
    private void addTemplate(){
        //model.addTemplate(canvas.getMiddle());
    }

    @FXML
    private void saveTemplate(){
        //hämta template från en lista av sparade templates
    }*/
}
