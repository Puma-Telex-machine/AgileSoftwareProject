package frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Ellipse;

import java.awt.*;
import java.io.IOException;


public class AnchorPointController extends AnchorPane {

    @FXML
    Ellipse center;

    private boolean pressed = false;

    public AnchorPointController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("view/Anchorpoint.fxml")));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void arrowEvent(){
        pressed=true;
    }
    public void setNotPressed(){
        pressed=false;
    }

    public Point getMid(){
        return new Point((int)(center.getCenterX()+this.getLayoutX()),(int)(center.getCenterY()+this.getLayoutY()));
    }
    public boolean getPressed(){
        return pressed;
    }
}

