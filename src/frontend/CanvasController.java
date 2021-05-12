package frontend;

import frontend.Observers.ArrowObserver;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Model;
import model.boxes.BoxType;
import model.facades.Observer;
import model.facades.BoxFacade;
import model.facades.RelationFacade;
import model.facades.RelationObserver;
import model.point.Scale;
import model.point.ScaledPoint;
import model.relations.ArrowType;
import model.relations.Relation;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class CanvasController extends AnchorPane implements Observer, ArrowObserver, RelationObserver, BoxPressedListener {

    VariableEditorController variableEditor;
    MethodEditorController methodEditor;

    @FXML
    private AnchorPane arrowMenu, menuPane, contextMenu;
    @FXML
    private ComboBox<ArrowType> arrowTypeComboBox;

    Model model = Model.getModel();

    List<BoxController> boxes = new ArrayList<>();

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

        model.addObserver(this);
    }

    @Override
    public void addBox(BoxFacade b) {
        BoxController box = new BoxController(b, variableEditor, methodEditor, this);
        this.getChildren().add(box);
        boxes.add(box);
        box.toggleCircleVisibility(!toggleOn);
        box.boxPressedSubscribe(this);
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

                model.addRelation(arrowBox.getBox(), offsetFrom, box.getBox(), offsetTo, ArrowType.ASSOCIATION);
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
    public void updateRelation(RelationFacade relation){
        removeRelation(relation);
        addArrow(relation);
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

    private void removeRelation(RelationFacade r){
        Arrow arrow = relationMap.get(r);
        this.getChildren().remove(arrow);
        arrows.remove(arrow);
        relationMap.remove(arrow);
        arrowMap.remove(r);
        model.removeRelation(r);
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
    private List<Arrow> clickedArrow = new ArrayList<>();

    @FXML
    private void handleArrowMenu(MouseEvent e) {
        List<Arrow> closest = new ArrayList<>();
        double min = 10000;
        for (Arrow a : arrows) {
            double distance = a.getDistaceFromClick(e);
            if(distance == min){
                closest.add(a);
            }
            if (distance < min) {
                min = distance;
                closest.clear();
                closest.add(a);
            }
        }
        if (min <= 15) {
            //trying to merge dragarrow into existing arrow
            if (makingArrow) {
                ScaledPoint offset = new ScaledPoint (Scale.Frontend,(int) (arrowStart.getX()-arrowBox.getLayoutX()),(int) (arrowStart.getY()-arrowBox.getLayoutY()));
                model.addRelation(arrowBox.getBox(),offset,arrowMap.get(closest.get(0)));
            }
            else{
                clickedArrow = closest;
                arrowTypeComboBox.getSelectionModel().select(closest.get(0).getType());
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
    private void handleContextAddBox(MouseEvent e) {
        model.addBox(new ScaledPoint(Scale.Frontend, (int) contextMenu.getLayoutX() - 80, (int) contextMenu.getLayoutY() - 35), BoxType.BOX);
        closeMenu(e);
        e.consume();
    }

    @FXML
    private void deleteArrow(Event e) {
        for (Arrow a:clickedArrow) {
            this.getChildren().remove(a);
            arrows.remove(a);
        }
        closeMenu(e);
        e.consume();
    }

    @FXML
    private void changeArrow(Event e) {
        ArrowType type = arrowTypeComboBox.getValue();
        for (Arrow a:clickedArrow) {
            a.setType(type);
            arrowMap.get(a).changeRelation(type);
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

    public void clearBoxes() {
        this.getChildren().removeAll();
        for (int i = 0; i < boxes.size(); i++) {
            deleteBox(boxes.get((i)));
        }
        boxes = new ArrayList<>();
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
    public void deleteSelectedBoxes()
    {
        for(int i = 0; i < selection.size(); i++)
        {
            deleteBox(selection.get(i));
        }
    }

    /**
     * Deletes the box
     * @param box
     */
    private void deleteBox(BoxController box)
    {
        box.setVisible(false); //todo: Properly remove items here
    }

    /**
     * Is called when a box is pressed
     * @param box the box that was pressed
     */
    public void pressedBox(BoxController box)
    {
        if(!multiSelect) selection.clear();
        selection.add(box);
    }
}
