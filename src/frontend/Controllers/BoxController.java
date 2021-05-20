package frontend.Controllers;

import frontend.Observers.ArrowObservable;
import frontend.Observers.ArrowObserver;
import global.Observer;
import global.TextWidthCalculator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;
import model.facades.AttributeFacade;
import model.boxes.BoxFacade;
import model.facades.MethodFacade;
import global.point.Scale;
import global.point.ScaledPoint;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * controller for the boxes in frontend
 */
public class BoxController extends AnchorPane implements ArrowObservable, Observer {
    @FXML
    private TextField nameField;
    @FXML
    private Label name, identifier;
    @FXML
    private VBox methods, variables, vBox,bigVBox;
    @FXML
    private Line line,line1;

    //for dragging box when editing name
    @FXML
    AnchorPane blockpane1, blockpane2;

    VariableEditorController variableEditor;
    MethodEditorController methodEditor;

    private Map<Label, String> methodMap = new HashMap<Label, String>();
    private Map<Label, String> variableMap = new HashMap<Label, String>();

    private BoxFacade box;

    private ArrowObserver arrowObserver;
    private List<BoxPressedListener> pressedListeners = new ArrayList<>();
    List<AnchorPointController> anchorPoints = new ArrayList<>();

    public BoxController(BoxFacade box, VariableEditorController VEC, MethodEditorController MEC, ArrowObserver arrowObserver) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("..//view/Box.fxml")));

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
        switch (box.getType()) {
            case CLASS -> {
                //remove typeidentifier and move components to work accordingly
                //todo check that this works
                blockpane1.setLayoutY(6);
                blockpane2.setLayoutY(30);
                nameField.setLayoutY(6);
                name.setPadding(new Insets(12,0,12,0));
                bigVBox.getChildren().remove(identifier);
            }
            case INTERFACE -> identifier.setText("<<Interface>>");

            case ABSTRACT_CLASS -> identifier.setText("<Abstract>");

            case ENUM -> identifier.setText("Enum");
        }

        this.arrowObserver = arrowObserver;
        this.box = box;
        hideCircles();

        //init box with name
        name.setText(box.getName());
        nameField.setText(box.getName());
        //box.setName(name.getText());
        update();
        //rezises namefield to fit whole name
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            clearAnchors();
            double width = TextWidthCalculator.getInstance().computeTextWidthName(nameField.getText());
            nameField.setPrefWidth(width);
            updateLines(width);
        });


        box.subscribe(this);
    }

    private void updateLines(double width){
        this.setWidth(0);
        width=Math.max(width+3,90);
        line.setStartX(0);
        line1.setStartX(0);
        line.setEndX(width);
        line1.setEndX(width);
    }


    private Point lastSize = null;

    private void clearAnchors(){
        for (AnchorPane anchor:anchorPoints) {
            this.getChildren().remove(anchor);
        }
        anchorPoints.clear();
    }
    private void addAnchors(){
        //top anchors
        for(int i = Scale.Frontend.xScale;i<box.getWidthAndHeight().getX(Scale.Frontend);i+=Scale.Frontend.xScale){
            AnchorPointController point = new AnchorPointController();
            this.getChildren().add(point);
            point.setLayoutX(i-7.5);
            AnchorPane.setTopAnchor(point, -6.0);
            anchorPoints.add(point);
        }

        //bottom anchors
        for(int i = Scale.Frontend.xScale;i<box.getWidthAndHeight().getX(Scale.Frontend);i+=Scale.Frontend.xScale){
            AnchorPointController point = new AnchorPointController();
            this.getChildren().add(point);
            point.rotateProperty().setValue(180);
            point.setLayoutX(i-7.5);
            AnchorPane.setBottomAnchor(point, -6.0);
            anchorPoints.add(point);
        }
        //left anchors
        for(int i = Scale.Frontend.yScale;i<box.getWidthAndHeight().getY(Scale.Frontend);i+=Scale.Frontend.yScale){
            AnchorPointController point = new AnchorPointController();
            this.getChildren().add(point);
            point.rotateProperty().setValue(270);
            point.setLayoutY(i-5);
            AnchorPane.setLeftAnchor(point, -8.5);
            anchorPoints.add(point);
        }
        //right anchors
        for(int i = Scale.Frontend.yScale;i<box.getWidthAndHeight().getY(Scale.Frontend);i+=Scale.Frontend.yScale){
            AnchorPointController point = new AnchorPointController();
            this.getChildren().add(point);
            point.rotateProperty().setValue(90);
            point.setLayoutY(i-5);
            AnchorPane.setRightAnchor(point, -8.5);
            anchorPoints.add(point);
        }
    }
    private void updateAnchorPoints() {

        //no update if size not changed
        Point size = box.getWidthAndHeight().getPoint(Scale.Frontend);
        if(lastSize==null||!lastSize.equals(size)){
            lastSize = box.getWidthAndHeight().getPoint(Scale.Frontend);
            clearAnchors();
            addAnchors();
            hideCircles();
        }
    }

    //region dragging boxes
    private boolean moving = false;
    private double offsetX = 0;
    private double offsetY = 0;

    /**
     * method for moving a box through dragging
     */
    public void dragBox(double x, double y)
    {
        variableEditor.setVisible(false);
        methodEditor.setVisible(false);
        if (!moving) {
            offsetX = x;
            offsetY = y;
            moving = true;
        }
        //todo fix max borders

        double posX=0;
        double posY=0;
        //move X
        if (this.getLayoutX() + x - offsetX > 0) {
            posX=this.getLayoutX()+ x - offsetX;
        }
        //move Y
        if (this.getLayoutY() + y - offsetY > 0) {
            posY = this.getLayoutY()+ y - offsetY;
        }

        //add fakeMove

        int X = new ScaledPoint(Scale.Frontend,posX,posY).getX(Scale.Frontend);
        int Y = new ScaledPoint(Scale.Frontend,posX,posY).getY(Scale.Frontend);


        this.setLayoutX(X+1);
        this.setLayoutY(Y+1);
    }

    /**
     * method for letting go of box, updates backend
     */
    public void handleLetGo(){
        if(moving) {
            moving = false;
            box.setAndUpdatePosition(new ScaledPoint(Scale.Frontend, (int) this.getLayoutX(), (int) this.getLayoutY()));
        }
    }
    //endregion
    //region methods

    /**
     * Adding a new method onto box
     */
    @FXML
    private void addMethod(MouseEvent e) {
        variableEditor.setVisible(false);
        methodEditor.setVisible(true);
        methodEditor.toFront();
        methodEditor.setLayoutX(this.getLayoutX() - variableEditor.getWidth());
        methodEditor.setLayoutY(this.getLayoutY() + methods.getLayoutY() + 15 * methods.getChildren().size()+10 - methodEditor.getHeight() / 2);
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
    private void addVariable(MouseEvent e) {
        methodEditor.setVisible(false);
        variableEditor.setVisible(true);
        variableEditor.toFront();
        variableEditor.setLayoutX(this.getLayoutX() - variableEditor.getWidth());
        variableEditor.setLayoutY(this.getLayoutY() + variables.getLayoutY() + 15 * variables.getChildren().size()+10 - variableEditor.getHeight() / 2);
        variableEditor.EditVariable(box);
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
    private void editVariable(AttributeFacade variable, AnchorPane pos) {
        methodEditor.setVisible(false);
        variableEditor.setVisible(true);
        variableEditor.toFront();
        variableEditor.setLayoutX(this.getLayoutX() - variableEditor.getWidth());
        variableEditor.setLayoutY(this.getLayoutY() + variables.getLayoutY() + 8 + pos.getLayoutY() - variableEditor.getHeight() / 2);
        variableEditor.EditVariable(variable, box);
    }

    /**
     * Editing a method on box
     */
    @FXML
    private void editMethod(MethodFacade method, AnchorPane pos) {
        variableEditor.setVisible(false);
        methodEditor.setVisible(true);
        methodEditor.toFront();
        methodEditor.setLayoutX(this.getLayoutX() - methodEditor.getWidth());
        methodEditor.setLayoutY(this.getLayoutY() + methods.getLayoutY() + 8 + pos.getLayoutY() - methodEditor.getHeight() / 2);
        methodEditor.EditMethod(method, box);
    }

    //endregion
    //region arrows
    @FXML
    private void hideCircles() {
        if (circleToggle) return;
        for (AnchorPointController e : anchorPoints) {
            e.setVisible(false);
        }
    }

    @FXML
    private void showCircles() {
        this.toFront();
        if (circleToggle) return;
        for (AnchorPointController e : anchorPoints) {
            e.setVisible(true);
            e.toFront();
        }
    }

    private boolean circleToggle = false;

    /**
     * for toggeling circles when adding arrows
     *
     * @param toggle toggle to set
     */
    public void toggleCircleVisibility(boolean toggle) {
        if (toggle) {
            circleToggle = false;
            hideCircles();
        } else {
            showCircles();
            circleToggle = true;
        }
    }

    /**
     * notifies the arrowObserver when anchorpoints pressed
     *
     * @param e onCLick
     */
    @Override
    public void notifyArrowEvent(MouseEvent e) {
        for (AnchorPointController a : anchorPoints) {
            if (a.getPressed()) {
                a.setNotPressed();
                arrowObserver.arrowEvent(new Point((int) (a.getMid().x + this.getLayoutX()), (int) (a.getMid().y + this.getLayoutY())), this);
            }
        }
        showCircles();

        for (int i = 0; i < pressedListeners.size(); i++)
        {
            pressedListeners.get(i).pressedBox(this);
        }
        e.consume();
    }

    //endregion
    //region name
    private boolean changeable = true;

    /**
     * updates the name and moves the editField to back
     */
    @FXML
    private void updateName() {
        box.setName(nameField.getText());
        nameField.toBack();
        blockpane1.toBack();
        blockpane2.toBack();
        name.setText(nameField.getText());
        this.requestFocus();
        addAnchors();
        update();
    }

    /**
     * name pressed, bring editField to front
     */
    @FXML
    private void changeName() {
        if (!changeable) {
            changeable = true;
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
    private void unableToChangeName() {
        changeable = false;
    }

    //endregion
    //region get/setters
    public String getName() {
        return name.getText();
    }

    public BoxFacade getBox() {
        return box;
    }
    //endregion

    /**
     * Updates all of the graphics of this box
     */
    public void update() {

        System.out.println("Updating box in Frontend" + this);
        variables.getChildren().clear();
        methods.getChildren().clear();

        List<AttributeFacade> variableData = box.getAttributes();
        List<MethodFacade> methodData = box.getMethods();
        name.setText(box.getName());
        nameField.setText(box.getName());

        for (int i = 0; i < variableData.size(); i++) {
            BoxAttributeTextController attribute = new BoxAttributeTextController( variableData.get(i).getString());
            variables.getChildren().add(attribute);
            AttributeFacade var = variableData.get(i);
            attribute.setOnMousePressed((Action) -> editVariable(var, attribute));

            //updateWidthCalculator with font of attributes and methods
            TextWidthCalculator.getInstance().setName(attribute.getFont());
        }
        TextWidthCalculator.getInstance().setOffset(20);
        TextWidthCalculator.getInstance().setName(nameField.getFont());


        for (int i = 0; i < methodData.size(); i++) {
            BoxAttributeTextController attribute = new BoxAttributeTextController(methodData.get(i).getString());
            methods.getChildren().add(attribute);
            MethodFacade met = methodData.get(i);
            attribute.setOnMousePressed((Action) -> editMethod(met, attribute));
        }

        //set box size -2 +1 to make sure no overlap (border of 1px outside both sides and 1 extra since ending on 30 and starting on 30)
        ScaledPoint widthAndHeight = box.getWidthAndHeight();
        ScaledPoint position = box.getPosition();
        this.setWidth(widthAndHeight.getX(Scale.Frontend)-3);
        this.setPrefHeight(widthAndHeight.getY(Scale.Frontend));
        this.setLayoutY(position.getY(Scale.Frontend)+1);
        this.setLayoutX(position.getX(Scale.Frontend)+1);
        line.setEndX(this.getWidth());
        line1.setEndX(this.getWidth());

        updateAnchorPoints();
    }

    /**
     * Adds the listener as a listener for the pressed event on this box
     * @param listener
     */
    public void boxPressedSubscribe(BoxPressedListener listener)
    {
        pressedListeners.add(listener);
    }

    /**
     * Deletes the box
     */
    public void deleteBox()
    {
        box.deleteBox();
        //this.setVisible(false); //todo: Properly remove items here
    }

    public void closeAttributeEditors()
    {
        methodEditor.setVisible(false);
        variableEditor.setVisible(false);
    }
}