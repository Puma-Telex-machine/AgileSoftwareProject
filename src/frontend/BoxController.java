package frontend;

import frontend.Observers.ArrowObservable;
import frontend.Observers.ArrowObserver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.shape.Ellipse;
import model.MethodData;
//import model.VariableData;
import model.facades.BoxFacade;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class BoxController extends AnchorPane implements ArrowObservable {
    @FXML
    private TextField nameField;
    @FXML
    private Label name;
    @FXML
    private VBox methods;
    @FXML
    private VBox variables;

    //for dragging box when editing name
    @FXML
    AnchorPane blockpane1,blockpane2;

    VariableEditorController variableEditor;
    MethodEditorController methodEditor;

    private Map<Label,String> methodMap = new HashMap<Label,String>();
    private Map<Label,String> variableMap = new HashMap<Label,String>();

    private BoxFacade box;

    private ArrowObserver arrowObserver;

    List<AnchorPointController> anchorPoints = new ArrayList<>();

    public BoxController(BoxFacade box,VariableEditorController VEC,MethodEditorController MEC,ArrowObserver arrowObserver){

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
        this.arrowObserver=arrowObserver;
        this.box = box;
        hideCircles();
        this.setLayoutX(box.getPosition().x);
        this.setLayoutY(box.getPosition().y);

        initAnchors();
    }
    private void initAnchors(){
        AnchorPointController p1 = new AnchorPointController();
        AnchorPointController p2 = new AnchorPointController();
        AnchorPointController p3 = new AnchorPointController();
        AnchorPointController p4 = new AnchorPointController();
        this.getChildren().add(p1);
        this.getChildren().add(p2);
        this.getChildren().add(p3);
        this.getChildren().add(p4);

        p2.rotateProperty().setValue(180);
        p3.rotateProperty().setValue(270);
        p4.rotateProperty().setValue(90);

        p1.setLayoutX(65);
        AnchorPane.setTopAnchor(p1, -7.0);
        p2.setLayoutX(65);
        AnchorPane.setBottomAnchor(p2, -7.0);
        p3.setLayoutY(28);
        AnchorPane.setLeftAnchor(p3, -10.0);
        p4.setLayoutY(28);
        AnchorPane.setRightAnchor(p4, -10.0);
        anchorPoints.add(p1);
        anchorPoints.add(p2);
        anchorPoints.add(p3);
        anchorPoints.add(p4);
        hideCircles();
    }

    //region dragging boxes
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
        box.setPosition(new Point((int)this.getLayoutX(),(int)this.getLayoutY()));
        //for snap to grid
        this.setLayoutX(box.getPosition().x);
        this.setLayoutY(box.getPosition().y);
        event.consume();
    }
    //endregion
    //region methods
    @FXML
    private void addMethod(MouseEvent e){
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
        variableEditor.setVisible(true);
        variableEditor.toFront();
        variableEditor.setLayoutX(this.getLayoutX()-variableEditor.getWidth());
        variableEditor.setLayoutY(this.getLayoutY()+this.getHeight()/2-variableEditor.getHeight()/2);
        //variableEditor.EditVariable(box);

        //add lamda expression  ish currentEditArgument.argumentTypeField.setOnAction((Action) -> editVariable(variableData)

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

    private void updateMethods(List<MethodData> methods){
        //todo
        //add lamda function  ish currentEditArgument.argumentTypeField.setOnAction((Action) -> editVariable(variableData)
    }

    //endregion
    //region arrows
    @FXML
    private void hideCircles(){
        if(circleToggle) return;
        for (AnchorPointController e:anchorPoints) {
            e.setVisible(false);
        }
    }
    @FXML
    private void showCircles(){
        if(circleToggle) return;
        for (AnchorPointController e:anchorPoints) {
            e.setVisible(true);
            e.toFront();
        }
    }
    private boolean circleToggle = false;

    public void toggleCircleVisibility(){
        if(circleToggle){
            circleToggle=false;
            hideCircles();
        }
        else{
            showCircles();
            circleToggle=true;
        }
    }
    @Override
    public void notifyArrowEvent(MouseEvent e) {
        System.out.println("box");
        for (AnchorPointController a:anchorPoints) {
            if(a.getPressed()){
                System.out.println("box register press on anchor");
                a.setNotPressed();
                arrowObserver.arrowEvent(new Point ((int)(a.getMid().x+this.getLayoutX()),(int)(a.getMid().y+this.getLayoutY())),this);
            }
        }
        showCircles();
        e.consume();
    }
    //endregion
    //region name
    @FXML
    private void updateName(){
        //todo wait for backend to implement
        //box.setName(nameField.getText());
        nameField.toBack();
        blockpane1.toBack();
        blockpane2.toBack();
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
        blockpane1.toFront();
        blockpane2.toFront();
        showCircles();
        nameField.requestFocus();
    }
    @FXML
    private void unableToChangeName(){
        changeable=false;
    }
    //endregion
    //region get/setters
    public String getName(){
        return name.getText();
    }
    public BoxFacade getBox(){
        return box;
    }
    //endregion
}
