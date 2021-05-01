package frontend;

import frontend.Observers.ArrowObservable;
import frontend.Observers.ArrowObserver;
import frontend.Observers.UiObserver;
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
import model.VariableData;
import model.boxes.BoxType;
import model.facades.BoxFacade;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * controller for the boxes in frontend
 */
public class BoxController extends AnchorPane implements ArrowObservable, UiObserver {
    @FXML
    private TextField nameField;
    @FXML
    private Label name,identifier;
    @FXML
    private VBox methods, variables,vBox;

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

        //set boxType
        switch(box.getType()){
            case CLASS:
                //remove typeidentifier and move components to work accordingly
                blockpane1.setLayoutY(0);
                blockpane2.setLayoutY(26);
                nameField.setLayoutY(1);
                vBox.getChildren().remove(identifier);
                break;
            case INTERFACE:
                identifier.setText("<<Interfacew>>");
                vBox.getChildren().remove(variables);
                //remove line and +
                vBox.getChildren().remove(6);
                vBox.getChildren().remove(5);
                break;
            case ABSTRACTCLASS:
                identifier.setText("<Abstract>");
                break;
            case ENUM:
                identifier.setText("Enum");
                vBox.getChildren().remove(methods);
                vBox.getChildren().remove(variables);
                //remove lines and +'s
                vBox.getChildren().remove(5);
                vBox.getChildren().remove(4);
                vBox.getChildren().remove(3);
                vBox.getChildren().remove(2);
                break;
        }

        this.arrowObserver=arrowObserver;
        this.box = box;
        hideCircles();

        this.setLayoutX(box.getPosition().x);
        this.setLayoutY(box.getPosition().y);

        initAnchors();
        box.subscribe(this);
    }

    private void initAnchors(){
        //todo add dynamicly
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
    private boolean moving = false;
    private double offsetX = 0;
    private double offsetY = 0;

    /**
     * method for moving a box through dragging
     * @param event event of the mouseDrag
     */
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

    /**
     * method for letting go of box, updates backend
     * @param event mouseRelease
     */
    @FXML
    private void handleLetGo(MouseEvent event){
        moving=false;
        moving=false;
        box.setPosition(new Point((int)this.getLayoutX(),(int)this.getLayoutY()));
        //for snap to grid
        this.setLayoutX(box.getPosition().x);
        this.setLayoutY(box.getPosition().y);
        event.consume();
    }
    //endregion
    //region methods

    /**
     * Adding a new method onto box
     */
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
    /**
     * Adding a new variable onto box
     */
    @FXML
    private void addVariable(MouseEvent e){
        methodEditor.setVisible(false);
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
    /**
     * Editing a variable on box
     */
    @FXML
    private void editVariable(){
        //todo
        //box.getVariableData()
    }

    /**
     * Editing a method on box
     */
    @FXML
    private void editMethod(){
        //todo
        //box.getVariableData()
    }

    private void updateMethods(List<MethodData> methods){
        //todo
        //add lamda function  ish currentEditArgument.argumentTypeField.setOnAction((Action) -> editVariable(variableData)
    }

    private void updateVariables(List<VariableData> varibles){
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

    /**
     * for toggeling circles when adding arrows
     * @param toggle toggle to set
     */
    public void toggleCircleVisibility(boolean toggle){
        if(toggle){
            circleToggle=false;
            hideCircles();
        }
        else{
            showCircles();
            circleToggle=true;
        }
    }

    /**
     * notifies the arrowObserver when anchorpoints pressed
     * @param e onCLick
     */
    @Override
    public void notifyArrowEvent(MouseEvent e) {
        for (AnchorPointController a:anchorPoints) {
            if(a.getPressed()){
                a.setNotPressed();
                arrowObserver.arrowEvent(new Point ((int)(a.getMid().x+this.getLayoutX()),(int)(a.getMid().y+this.getLayoutY())),this);
            }
        }
        showCircles();
        e.consume();
    }
    //endregion
    //region name

    private boolean changeable=true;
    /**
     * updates the name and moves the editField to back
     */
    @FXML
    private void updateName(){
        box.setName(nameField.getText());
        nameField.toBack();
        blockpane1.toBack();
        blockpane2.toBack();
        name.setText(nameField.getText());
        this.requestFocus();
    }

    /**
     * name pressed, bring editField to front
     */
    @FXML
    private void changeName(){
        if(!changeable){
            changeable=true;
            return;
        }
        nameField.setText(name.getText());
        nameField.toFront();
        //blockpanes to be able to drag when editing
        blockpane1.toFront();
        blockpane2.toFront();
        showCircles();
        nameField.requestFocus();
    }

    /**
     * locks namechange when dragging on name
     */
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

    public void update()
    {
        VariableData[] variableData = box.getVariables();
        MethodData[] methodData = box.getMethods();

        for (int i = 0; i < variableData.length; i++)
        {
            String variable = "";

            switch (variableData[i].visibility)
            {
                case PUBLIC: variable += "+"; break;
                case PRIVATE: variable += "-"; break;
                case PROTECTED: variable += "#"; break;
                case PACKAGE_PRIVATE: variable += "~"; break;
            }

            variable += " ";
            variable += variableData[i].name;
            variable += ": ";
            variable += variableData[i].variableType;

            variables.getChildren().add(new BoxAttributeTextController(variable));
        }
    }
}
