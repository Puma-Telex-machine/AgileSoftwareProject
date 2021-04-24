import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.MethodData;
import model.VariableData;
import model.boxes.Box;
import model.facades.BoxFacade;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoxController extends AnchorPane {
    @FXML
    private Label name;
    @FXML
    private VBox methods;
    @FXML
    private VBox variables;
    @FXML
    AnchorPane circlePane;

    VariableEditorController variableEditor;
    MethodEditorController methodEditor;

    private Map<Label,String> methodMap = new HashMap<Label,String>();
    private Map<Label,String> variableMap = new HashMap<Label,String>();

    private BoxFacade box;

    public BoxController(BoxFacade box,VariableEditorController VEC,MethodEditorController MEC){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("view/Box.fxml")));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        variableEditor = VEC;
        methodEditor = MEC;
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.box = box;
        circlePane.setVisible(false);
        this.setLayoutX(box.getPosition().x);
        this.setLayoutY(box.getPosition().y);
    }

    //for moving box
    private boolean moving = false;
    private double offsetX = 0;
    private double offsetY = 0;

    @FXML
    private void handleDrag(MouseEvent event){
        if(!moving){
            offsetX = event.getX();
            offsetY = event.getY();
            moving=true;
        }
        //todo fix borders
        this.setLayoutX(this.getLayoutX()+ event.getX() - offsetX);
        this.setLayoutY(this.getLayoutY()+ event.getY() - offsetY);
        event.consume();
    }

    @FXML
    private void handleLetGo(MouseEvent event){
        moving=false;
        box.setPosition(new Point((int)this.getLayoutX(),(int)this.getLayoutY()));
        event.consume();
    }
    @FXML
    private void addMethod(MouseEvent e){

        System.out.println("addmethod");
        methodEditor.setVisible(true);
        methodEditor.setLayoutX(this.getLayoutX()-variableEditor.getWidth());
        methodEditor.setLayoutY(this.getLayoutY()+this.getHeight()/2-methodEditor.getHeight()/2);
        methodEditor.EditMethod(box);

        /*
        String method = "+ getNumber() : int";
        methodList.add(method);
        Label tmp = new Label();
        Paint p = new Color(0.86,0.86,0.86,1);
        tmp.setTextFill(p);
        tmp.setText(method);
        methods.getChildren().add(tmp);*/
        e.consume();
    }
    @FXML
    private void addVariable(MouseEvent e){

        variableEditor.setVisible(true);
        variableEditor.setLayoutX(this.getLayoutX()-variableEditor.getWidth());
        variableEditor.setLayoutY(this.getLayoutY()+this.getHeight()/2-variableEditor.getHeight()/2);
        variableEditor.EditVariable(box);


        /*String method = "+ variable : bool";
        variableList.add(method);
        Label tmp = new Label();
        Paint p = new Color(0.86,0.86,0.86,1);
        tmp.setTextFill(p);
        tmp.setText(method);
        variables.getChildren().add(tmp);*/
        e.consume();
    }
    @FXML
    private void editVariable(){
        //todo
        //box.getVariableData()
    }

    public void updateMethods(List<MethodData> methods){
        //todo
        //add lamda function  ish currentEditArgument.argumentTypeField.setOnAction((Action) -> editVariable(variableData)
    }


    @FXML
    private void hoverEnter(){
        circlePane.setVisible(true);
    }
    @FXML
    private void hoverExit(){
        circlePane.setVisible(false);
    }
    private boolean creatingArrow=false;
    @FXML
    private void startArrow(MouseEvent event){
        if(!creatingArrow){
            name.setText("creating arrow");
            creatingArrow=true;
        }
        //todo find closest anchorpoint
        //todo observerpattern
        event.consume();
    }
    @FXML
    private void attachArrow(MouseEvent event){
        creatingArrow=false;
        name.setText("attaching arrow");

        //todo observerpattern
    }
    public BoxFacade getBox(){
        return box;
    }
}
