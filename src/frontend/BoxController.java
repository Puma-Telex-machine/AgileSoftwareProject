package frontend;

import frontend.Observers.ArrowObservable;
import frontend.Observers.ArrowObserver;
import frontend.Observers.UiObserver;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import model.MethodData;
import model.VariableData;
import model.boxes.Visibility;
import model.facades.AttributeFacade;
import model.facades.BoxFacade;
import model.facades.MethodFacade;
import model.point.Scale;
import model.point.ScaledPoint;
import com.sun.javafx.scene.control.skin.Utils;

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
    private Label name, identifier;
    @FXML
    private VBox methods, variables, vBox;
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

    List<AnchorPointController> anchorPoints = new ArrayList<>();

    public BoxController(BoxFacade box, VariableEditorController VEC, MethodEditorController MEC, ArrowObserver arrowObserver) {

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
        switch (box.getType()) {
            case CLASS:
                //remove typeidentifier and move components to work accordingly
                //todo check that this works
                blockpane1.setLayoutY(7);
                blockpane2.setLayoutY(26);
                nameField.setLayoutY(7);
                vBox.setLayoutY(7);
                vBox.getChildren().remove(identifier);
                break;
            case INTERFACE:
                identifier.setText("<<Interface>>");
                break;
            case ABSTRACT_CLASS:
                identifier.setText("<Abstract>");
                break;
            case ENUM:
                identifier.setText("Enum");
                break;
        }

        this.arrowObserver = arrowObserver;
        this.box = box;
        hideCircles();

        //init box with name and set this boxcontroller to the size and position of the box
        box.setName(name.getText());
        this.setLayoutX(box.getPosition().getX(Scale.Frontend));
        this.setLayoutY(box.getPosition().getY(Scale.Frontend));
        this.setWidth(box.getWidthAndHeight().getX(Scale.Frontend));
        this.setHeight(box.getWidthAndHeight().getY(Scale.Frontend));

        //dont ask rezises namefield to fit whole name
        nameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                double width = TextUtils.computeTextWidth(nameField.getFont(), nameField.getText(), 0.0D) + 20;
                nameField.setPrefWidth(width);
                updateLines(width);
            }
        });


        update();

        box.subscribe(this);
    }

    private void updateLines(double width){
        width=Math.max(width+3,90);
        width=Math.max(width,(int)this.getWidth()-3);
        line.setStartX(0);
        line1.setStartX(0);
        line.setEndX(width);
        line1.setEndX(width);
    }
    private Point lastSize = null;
    private void updateAnchorPoints() {

        //no update if size not changed
        Point size = box.getWidthAndHeight().getPoint(Scale.Frontend);
        if(lastSize!=null&&lastSize.equals(size)) return;

        lastSize = box.getWidthAndHeight().getPoint(Scale.Frontend);

        for (AnchorPane anchor:anchorPoints) {
            this.getChildren().remove(anchor);
        }
        anchorPoints.clear();

        //top anchors
        for(int i = Scale.Frontend.xScale;i<box.getWidthAndHeight().getX(Scale.Frontend);i+=Scale.Frontend.xScale){
            AnchorPointController point = new AnchorPointController();
            this.getChildren().add(point);
            point.setLayoutX(i-7.5);
            AnchorPane.setTopAnchor(point, -5.0);
            anchorPoints.add(point);
        }

        //bottom anchors
        for(int i = Scale.Frontend.xScale;i<box.getWidthAndHeight().getX(Scale.Frontend);i+=Scale.Frontend.xScale){
            AnchorPointController point = new AnchorPointController();
            this.getChildren().add(point);
            point.rotateProperty().setValue(180);
            point.setLayoutX(i-7.5);
            AnchorPane.setBottomAnchor(point, -5.0);
            anchorPoints.add(point);
        }
        //left anchors
        for(int i = Scale.Frontend.yScale;i<box.getWidthAndHeight().getY(Scale.Frontend);i+=Scale.Frontend.yScale){
            AnchorPointController point = new AnchorPointController();
            this.getChildren().add(point);
            point.rotateProperty().setValue(270);
            point.setLayoutY(i-5);
            AnchorPane.setLeftAnchor(point, -7.5);
            anchorPoints.add(point);
        }
        //right anchors
        for(int i = Scale.Frontend.yScale;i<box.getWidthAndHeight().getY(Scale.Frontend);i+=Scale.Frontend.yScale){
            AnchorPointController point = new AnchorPointController();
            this.getChildren().add(point);
            point.rotateProperty().setValue(90);
            point.setLayoutY(i-5);
            AnchorPane.setRightAnchor(point, -7.5);
            anchorPoints.add(point);
        }

        hideCircles();
    }

    //region dragging boxes
    private boolean moving = false;
    private double offsetX = 0;
    private double offsetY = 0;

    /**
     * method for moving a box through dragging
     *
     * @param event event of the mouseDrag
     */
    @FXML
    private void handleDrag(MouseEvent event) {
        variableEditor.setVisible(false);
        methodEditor.setVisible(false);
        if (!moving) {
            offsetX = event.getX();
            offsetY = event.getY();
            moving = true;
        }
        //todo fix max borders

        double posX=0;
        double posY=0;
        //move X
        if (this.getLayoutX() + event.getX() - offsetX > 0) {
            posX=this.getLayoutX()+ event.getX() - offsetX;
        }
        //move Y
        if (this.getLayoutY() + event.getY() - offsetY > 0) {
            posY = this.getLayoutY()+ event.getY() - offsetY;
        }

        //todo this needs testing and refining
        this.setLayoutX(posX);
        this.setLayoutY(posY);


        notifyBoxDrag();

        event.consume();
    }

    /**
     * method for letting go of box, updates backend
     *
     * @param event mouseRelease
     */
    @FXML
    private void handleLetGo(MouseEvent event) {
        moving = false;
        box.setPosition(new ScaledPoint(Scale.Frontend, (int) this.getLayoutX(), (int) this.getLayoutY()));
        //for snap to grid
        this.setLayoutX(box.getPosition().getX(Scale.Frontend));
        this.setLayoutY(box.getPosition().getY(Scale.Frontend));
        event.consume();
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
        methodEditor.setLayoutY(this.getLayoutY() + this.getHeight() / 2 - methodEditor.getHeight() / 2);
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
        variableEditor.setLayoutY(this.getLayoutY() + this.getHeight() / 2 - variableEditor.getHeight() / 2);
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
        variableEditor.setLayoutY(this.getLayoutY() + variables.getLayoutY() + 25 + pos.getLayoutY() - variableEditor.getHeight() / 2);
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
        methodEditor.EditMethod(method, box);
        methodEditor.setLayoutX(this.getLayoutX() - variableEditor.getWidth());
        methodEditor.setLayoutY(this.getLayoutY() + methods.getLayoutY() + 25 + pos.getLayoutY() - methodEditor.getHeight() / 2);
    }

    private void updateMethods(List<MethodData> methods) {
        //todo
        //add lamda function  ish currentEditArgument.argumentTypeField.setOnAction((Action) -> editVariable(variableData)
    }

    private void updateVariables(List<VariableData> varibles) {
        //todo
        //add lamda function  ish currentEditArgument.argumentTypeField.setOnAction((Action) -> editVariable(variableData)
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
        e.consume();
    }

    @Override
    public void notifyBoxDrag() {
        arrowObserver.boxDrag(box);
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

        System.out.println("update");
        variables.getChildren().clear();
        methods.getChildren().clear();

        List<AttributeFacade> variableData = box.getAttributes();
        List<MethodFacade> methodData = box.getMethods();

        for (int i = 0; i < variableData.size(); i++) {
            BoxAttributeTextController attribute = new BoxAttributeTextController( variableData.get(i).getString());
            variables.getChildren().add(attribute);
            AttributeFacade var = variableData.get(i);
            attribute.setOnMousePressed((Action) -> editVariable(var, attribute));

        }

        for (int i = 0; i < methodData.size(); i++) {
            BoxAttributeTextController attribute = new BoxAttributeTextController(methodData.get(i).getString());
            methods.getChildren().add(attribute);
            MethodFacade met = methodData.get(i);
            attribute.setOnMousePressed((Action) -> editMethod(met, attribute));
        }

        //set box size
        this.setWidth(box.getWidthAndHeight().getX(Scale.Frontend));
        this.setHeight(box.getWidthAndHeight().getY(Scale.Frontend));
        this.setLayoutY(box.getPosition().getY(Scale.Frontend));
        this.setLayoutX(box.getPosition().getX(Scale.Frontend));
        line.setEndX(this.getWidth());
        line1.setEndX(this.getWidth());



        updateAnchorPoints();
    }

}


//dont mind this
class TextUtils {

    static final Text helper;
    static final double DEFAULT_WRAPPING_WIDTH;
    static final double DEFAULT_LINE_SPACING;
    static final String DEFAULT_TEXT;
    static final TextBoundsType DEFAULT_BOUNDS_TYPE;
    static {
        helper = new Text();
        DEFAULT_WRAPPING_WIDTH = helper.getWrappingWidth();
        DEFAULT_LINE_SPACING = helper.getLineSpacing();
        DEFAULT_TEXT = helper.getText();
        DEFAULT_BOUNDS_TYPE = helper.getBoundsType();
    }

    public static double computeTextWidth(Font font, String text, double help0) {
        // Toolkit.getToolkit().getFontLoader().computeStringWidth(field.getText(),
        // field.getFont());

        helper.setText(text);
        helper.setFont(font);

        helper.setWrappingWidth(0.0D);
        helper.setLineSpacing(0.0D);
        double d = Math.min(helper.prefWidth(-1.0D), help0);
        helper.setWrappingWidth((int) Math.ceil(d));
        d = Math.ceil(helper.getLayoutBounds().getWidth());

        helper.setWrappingWidth(DEFAULT_WRAPPING_WIDTH);
        helper.setLineSpacing(DEFAULT_LINE_SPACING);
        helper.setText(DEFAULT_TEXT);
        return d;
    }
}