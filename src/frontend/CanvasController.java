package frontend;

import frontend.Observers.ArrowObserver;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import model.Model;
import model.ModelFacade;
import model.boxes.BoxType;
import model.diagram.DiagramFacade;
import model.diagram.DiagramObserver;
import model.boxes.BoxFacade;
import model.relations.Relation;
import model.relations.RelationFacade;
import model.relations.RelationObserver;
import global.point.Scale;
import global.point.ScaledPoint;
import model.relations.ArrowType;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javafx.scene.shape.Rectangle;

public class CanvasController extends AnchorPane implements DiagramObserver, ArrowObserver, RelationObserver, BoxPressedListener {

    VariableEditorController variableEditor;
    MethodEditorController methodEditor;

    @FXML
    private AnchorPane arrowMenu, menuPane, contextMenu;
    @FXML
    private ComboBox<ArrowType> arrowTypeComboBox;

    ModelFacade model = Model.getModel();
    DiagramFacade diagram = model.getDiagram();

    List<BoxController> boxes = new ArrayList<>();

    private double mouseDownX;
    private double mouseDownY;

    private Rectangle selectionRectangle;

    public CanvasController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("view/Canvas.fxml")));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        arrowTypeComboBox.getItems().addAll(ArrowType.IMPLEMENTATION, ArrowType.INHERITANCE, ArrowType.ASSOCIATION, ArrowType.AGGREGATION, ArrowType.COMPOSITION, ArrowType.DEPENDANCY);
        arrowMenu.setVisible(false);

        variableEditor = new VariableEditorController();
        methodEditor = new MethodEditorController();
        this.getChildren().add(methodEditor);
        this.getChildren().add(variableEditor);
        variableEditor.setVisible(false);
        methodEditor.setVisible(false);

        menuPane.setVisible(false);
        arrowMenu.setVisible(false);
        contextMenu.setVisible(false);

        diagram.subscribe(this);
        //model.addObserver(this);
        clearSelection();

        selectionRectangle = new Rectangle();
        selectionRectangle.setStroke(Color.WHITE);
        selectionRectangle.setFill(Color.TRANSPARENT);
        selectionRectangle.getStrokeDashArray().addAll(5.0,5.0);
        this.getChildren().add(selectionRectangle);

        this.setOnMousePressed(e -> {
            selectionRectangle.setVisible(true);
            mouseDownX = e.getX();
            mouseDownY = e.getY();
            selectionRectangle.setX(mouseDownX);
            selectionRectangle.setY(mouseDownY);
            selectionRectangle.setWidth(0);
            selectionRectangle.setHeight(0);
        });

        this.setOnMouseDragged( e-> {
            clearSelection();
            selectionRectangle.setX(Math.min(e.getX(), mouseDownX));
            selectionRectangle.setWidth(Math.abs(e.getX() - mouseDownX));
            selectionRectangle.setY(Math.min(e.getY(), mouseDownY));
            selectionRectangle.setHeight(Math.abs(e.getY() - mouseDownY));
        });

        this.setOnMouseReleased(e -> {

            for (int i = 0; i < boxes.size(); i++)
            {
                double x = boxes.get(i).getBox().getPosition().getX(Scale.Frontend);
                double y = boxes.get(i).getBox().getPosition().getY(Scale.Frontend);
                if(selectionRectangle.getX() <=  x
                        && (selectionRectangle.getX() + selectionRectangle.getWidth()) >= x
                        && selectionRectangle.getY() <= y
                        && (selectionRectangle.getY() + selectionRectangle.getHeight()) >= y)
                {
                    selectBox(boxes.get(i));
                }
            }

            selectionRectangle.setVisible(false);
        });
    }

    @Override
    public void addBox(BoxFacade b) {
        BoxController box = new BoxController(b, variableEditor, methodEditor, this);
        this.getChildren().add(box);
        boxes.add(box);
        box.toggleCircleVisibility(!toggleOn);
        box.boxPressedSubscribe(this);
        box.setOnMouseDragged(e -> { draggingBox( e , box);});
        box.setOnMouseReleased(e->{letGoBox(e,box);});
        clearSelection();
    }

    private void draggingBox(MouseEvent e, BoxController box)
    {
        box.dragBox(e.getX(), e.getY());
        for (int i = 0; i < selection.size(); i++)
        {
            if(box != selection.get(i))
            {
                selection.get(i).dragBox(e.getX(), e.getY());
            }
        }
        e.consume();
    }
    private void letGoBox(MouseEvent e, BoxController box){
        box.handleLetGo();
        for (int i = 0; i < selection.size(); i++)
        {
            if(box != selection.get(i))
            {
                selection.get(i).handleLetGo();
            }
        }
        e.consume();
    }

    public Point getMiddle() {
        return new Point(500, 400);
    }

    //region arrowmaking

    private boolean makingArrow = false;
    private Arrow dragArrow;
    private BoxController arrowBox = null;
    private Point arrowStart;
    private boolean toggleOn = false;
    private List<Arrow> arrows = new ArrayList<>();
    private Dictionary<Arrow, RelationFacade> arrowMap = new Hashtable<>();
    private Dictionary<RelationFacade, Arrow> relationMap = new Hashtable<>();

    @Override
    public void arrowEvent(Point p, BoxController box) {
        //attach arrow
        if (makingArrow) {
            this.getChildren().removeAll(dragArrow);
            //box == arrowBox => aborting arrowcreation
            if (box != arrowBox) {

                ScaledPoint offsetTo = new ScaledPoint(Scale.Frontend, (int) (p.x - box.getLayoutX()), (int) (p.y - box.getLayoutY()));
                ScaledPoint offsetFrom = new ScaledPoint(Scale.Frontend, (int) (arrowStart.x - arrowBox.getLayoutX()), (arrowStart.y - arrowBox.getLayoutY()));
                diagram.createRelation(arrowBox.getBox(), offsetFrom, box.getBox(), offsetTo, ArrowType.ASSOCIATION);
            }
        }
        //start making arrow
        else {
            arrowBox = box;
            arrowStart = new Point(p.x, p.y);
            dragArrow = new Arrow(arrowStart, new Point(p.x, p.y),new ArrayList<>());
            this.getChildren().add(dragArrow);
        }
        toggleAnchorPoints();
        makingArrow = !makingArrow;
    }

    @Override
    public void addRelation(RelationFacade relation) {
       relation.subscribe(this);
       addArrow(relation);
    }

    @Override
    public void update(RelationFacade relation){
        removeArrow(relation);
        if(!relation.isDeleted()) addArrow(relation);
    }

    private void addArrow(RelationFacade relation){
        List<ScaledPoint> bends = relation.getPath();
        Arrow newArrow = new Arrow(bends);
        newArrow.setType(relation.getArrowType());
        this.getChildren().addAll(newArrow);
        newArrow.toBack();

        arrowMap.put(newArrow, relation);
        relationMap.put(relation,newArrow);
        arrows.add(newArrow);
    }
    private void removeArrow(RelationFacade r){
        Arrow arrow = relationMap.get(r);
        this.getChildren().remove(arrow);
        arrows.remove(arrow);
        relationMap.remove(r);
        arrowMap.remove(arrow);
    }

    /**
     * toggles all anchorpoints on all classes
     */
    private void toggleAnchorPoints() {
        for (BoxController box : boxes) {
            box.toggleCircleVisibility(toggleOn);
        }
        toggleOn = !toggleOn;
    }

    /**
     * updates the arrow when creating a arrow
     */
    @FXML
    private void dragArrow(MouseEvent e) {
        if (makingArrow) {
            this.getChildren().remove(dragArrow);
            dragArrow = new Arrow(arrowStart, new Point((int) e.getX(), (int) e.getY()),new ArrayList<>());
            this.getChildren().add(dragArrow);
            dragArrow.toBack();
        }
        e.consume();
    }

    private void removeDragArrow(){
        makingArrow = false;
        toggleAnchorPoints();
        this.getChildren().remove(dragArrow);
    }

    //endregion

    //region Menus
    private List<RelationFacade> clickedRelations = new ArrayList<>();

    @FXML
    private void handleArrowMenu(MouseEvent e) {
        List<RelationFacade> closest = new ArrayList<>();
        double min = 10000;
        for (Arrow a : arrows) {
            double distance = a.getDistaceFromClick(e);
            if(distance == min){
                closest.add(arrowMap.get(a));
            }
            if (distance < min) {
                min = distance;
                closest.clear();
                closest.add(arrowMap.get(a));
            }
        }
        if (min <= 15) {
            //trying to merge dragarrow into existing arrow
            if (makingArrow) {
                ScaledPoint offset = new ScaledPoint (Scale.Frontend,(int) (arrowStart.getX()-arrowBox.getLayoutX()),(int) (arrowStart.getY()-arrowBox.getLayoutY()));
                diagram.createRelation(arrowBox.getBox(),offset,closest.get(0).getTo(),closest.get(0).getOffsetTo(), ArrowType.ASSOCIATION);
            }
            else{
                clickedRelations = closest;
                arrowTypeComboBox.getSelectionModel().select(closest.get(0).getArrowType());
                openArrowMenu(e.getX(), e.getY());
            }
        }
        if(makingArrow){
            removeDragArrow();
        }
        e.consume();
    }

    private void openArrowMenu(double x, double y) {
        menuPane.setVisible(true);
        menuPane.toFront();
        arrowMenu.setLayoutX(x);
        arrowMenu.setLayoutY(y);
        arrowMenu.setVisible(true);
    }

    @FXML
    private void handleContextMenu(ContextMenuEvent e) {
        if (makingArrow) {
           removeDragArrow();
           e.consume();
           return;
        }
        contextMenu.setLayoutX(e.getX());
        contextMenu.setLayoutY(e.getY());
        contextMenu.setVisible(true);
        menuPane.setVisible(true);
        menuPane.toFront();
        e.consume();
    }

    @FXML
    private void handleContextAddBox(MouseEvent e, BoxType type) {
        diagram.createBox(new ScaledPoint(Scale.Frontend, (int) contextMenu.getLayoutX() - 80, (int) contextMenu.getLayoutY() - 35), type);
        closeMenu(e);
        e.consume();
    }
    @FXML
    private void addClass(MouseEvent e){
        handleContextAddBox(e,BoxType.CLASS);
    }
    @FXML
    private void addInterface(MouseEvent e){
        handleContextAddBox(e,BoxType.INTERFACE);
    }
    @FXML
    private void addAbstract(MouseEvent e){
        handleContextAddBox(e,BoxType.ABSTRACT_CLASS);
    }
    @FXML
    private void addEnum(MouseEvent e){
        handleContextAddBox(e,BoxType.ENUM);
    }

    @FXML
    private void deleteArrow(Event e) {
        for (RelationFacade r: clickedRelations) {
            Arrow a = relationMap.get(r);
            this.getChildren().remove(a);
            arrows.remove(a);
            r.remove();
            relationMap.remove(r);
            arrowMap.remove(a);
        }
        closeMenu(e);
        e.consume();
    }

    @FXML
    private void changeArrow(Event e) {
        ArrowType type = arrowTypeComboBox.getValue();
        for (RelationFacade r: clickedRelations) {
            relationMap.get(r).setType(type);
            r.changeRelationType(type);
        }
        closeMenu(e);
        e.consume();

    }

    @FXML
    private void closeMenu(Event e) {
        menuPane.setVisible(false);
        arrowMenu.setVisible(false);
        contextMenu.setVisible(false);
        menuPane.toBack();
        e.consume();
    }

    @FXML
    private void eventTrap(Event e) {
        e.consume();
    }

    //endregion

    /**
     * Deletes all the boxes on the current canvas
     */
    public void clearBoxes() {
        List<BoxController> tmp = boxes;
        for (int i = 0; i < tmp.size(); i++) {
            deleteBox(tmp.get((i)));
        }
        tmp.clear();
        boxes.clear();
        selection.clear();
        this.getChildren().clear();
    }

    private boolean multiSelect = false;
    private List<BoxController> selection = new ArrayList<>();

    /**
     * Start adding boxes to the selection list when pressing on them
     */
    public void startAddSelect()
    {
        multiSelect = true;
    }

    /**
     * Stops adding boxes to the selection list when pressing on them
     */
    public void endAddSelect()
    {
        multiSelect = false;
    }

    /**
     * Deletes the currently selected boxes
     */
    @FXML
    public void deleteSelectedBoxes()
    {
        for(int i = 0; i < selection.size(); i++)
        {
            deleteBox(selection.get(i));
        }
        selection.clear();
    }

    /**
     * Clears the selection list
     */
    private void clearSelection()
    {
        for (int i = 0; i < selection.size(); i++)
        {
            selection.get(i).getStyleClass().remove("border-selected");
            selection.get(i).getStyleClass().add("border");
        }
        selection.clear();
    }

    /**
     * Deletes the box
     * @param box
     */
    private void deleteBox(BoxController box)
    {
        boxes.remove(box);
        this.getChildren().remove(box);
        box.deleteBox();
    }

    /**
     * Is called when a box is pressed
     * @param box the box that was pressed
     */
    public void pressedBox(BoxController box)
    {
        if(!multiSelect)
            clearSelection();
        selectBox(box);
    }

    /**
     * sets the box as selected
     * @param box
     */
    private void selectBox(BoxController box)
    {
        selection.add(box);
        box.getStyleClass().remove("border");
        box.getStyleClass().add("border-selected");
    }
}
