package frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.shape.Ellipse;
import model.facades.MethodData;
import model.facades.BoxFacade;
import model.point.Scale;
import model.point.ScaledPoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoxController extends AnchorPane {
    @FXML
    private TextField nameField;
    @FXML
    private Label name;
    @FXML
    private VBox methods;
    @FXML
    private VBox variables;
    @FXML
    Ellipse circle1,circle2,circle3,circle4;

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
        hideCircles();

        ScaledPoint position = box.getPosition();

        this.setLayoutX(position.getX(Scale.Frontend));
        this.setLayoutY(position.getY(Scale.Frontend));
    }

    //for moving box
    private boolean moving = false;
    private double offsetX = 0;
    private double offsetY = 0;

    @FXML
    private void handleDrag(MouseEvent event){
        variableEditor.setVisible(false);
        methodEditor.setVisible(false);
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
        box.setPosition(new ScaledPoint(Scale.Frontend, this.getLayoutX(), this.getLayoutY()));

        ScaledPoint position = box.getPosition();

        //for snap to grid
        this.setLayoutX(position.getX(Scale.Frontend));
        this.setLayoutY(position.getY(Scale.Frontend));
        event.consume();
    }
    @FXML
    private void addMethod(MouseEvent e){
        variableEditor.setVisible(false);
        methodEditor.setVisible(true);
        methodEditor.toFront();
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
        methodEditor.setVisible(false);
        variableEditor.setVisible(true);
        variableEditor.toFront();
        variableEditor.setLayoutX(this.getLayoutX()-variableEditor.getWidth());
        variableEditor.setLayoutY(this.getLayoutY()+this.getHeight()/2-variableEditor.getHeight()/2);
        //variableEditor.EditVariable(box);


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

    private boolean creatingArrow=false;
    @FXML
    private void startArrow(MouseEvent event){
        if(!creatingArrow){
            creatingArrow=true;
        }
        //todo find closest anchorpoint
        //todo observerpattern
        event.consume();
    }
    @FXML
    private void attachArrow(MouseEvent event){
        creatingArrow=false;

        //todo observerpattern
    }

    public BoxFacade getBox(){
        return box;
    }
    @FXML
    private void hideCircles(){
        circle1.setVisible(false);
        circle2.setVisible(false);
        circle3.setVisible(false);
        circle4.setVisible(false);
    }
    @FXML
    private void showCircles(){
        circle1.setVisible(true);
        circle2.setVisible(true);
        circle3.setVisible(true);
        circle4.setVisible(true);
    }
    private void circlesToFront(){
        circle1.toFront();
        circle2.toFront();
        circle3.toFront();
        circle4.toFront();
    }
    @FXML
    private void updateName(){
        //todo wait for backend to implement
        //box.setName(nameField.getText());
        nameField.toBack();
        name.setText(nameField.getText());
        this.requestFocus();
    }
    private boolean changeable=true;
    @FXML
    private void changeName(){
        if(!changeable){
            changeable=true;
            return;
        }
        nameField.setText(name.getText());
        nameField.toFront();
        circlesToFront();
        nameField.requestFocus();
    }
    @FXML
    private void unableToChangeName(){
        changeable=false;
    }
}
