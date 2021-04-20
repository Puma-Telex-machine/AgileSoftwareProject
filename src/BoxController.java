import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.Box;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BoxController extends AnchorPane {
    @FXML
    Label name;
    @FXML
    VBox methods;
    @FXML
    VBox variables;

    List<String> methodList = new ArrayList<>();
    List<String> variableList = new ArrayList<>();

    Box box;
    public BoxController(Box box){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("view/Box.fxml")));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.box = box;
    }

    boolean moving = false;
    double x=0;
    double y=0;
    @FXML
    public void handleDrag(MouseEvent event){
        if(!moving){
            x=event.getX();
            y=event.getY();
            moving=true;
        }
        this.setLayoutX(this.getLayoutX()+event.getX()-x);
        this.setLayoutY(this.getLayoutY()+event.getY()-y);
        event.consume();
    }
    public void handleLetGo(MouseEvent event){
        moving = false;
        event.consume();
    }
    public void addMethod(){
        String method = "+ getNumber() : int";
        methodList.add(method);
        Label tmp = new Label();
        Paint p = new Color(0.86,0.86,0.86,1);
        tmp.setTextFill(p);
        tmp.setText(method);
        methods.getChildren().add(tmp);
    }
    public void addVariable(){
        String method = "+ varable : bool";
        variableList.add(method);
        Label tmp = new Label();
        Paint p = new Color(0.86,0.86,0.86,1);
        tmp.setTextFill(p);
        tmp.setText(method);
        variables.getChildren().add(tmp);
    }
}
