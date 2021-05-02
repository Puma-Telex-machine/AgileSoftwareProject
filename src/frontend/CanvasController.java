package frontend;

import frontend.Observers.ArrowObserver;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Model;
import model.Observer;
import model.facades.BoxFacade;
import model.relations.ArrowType;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CanvasController extends AnchorPane implements Observer, ArrowObserver {

    VariableEditorController variableEditor;
    MethodEditorController methodEditor;

    @FXML
    private AnchorPane arrowMenu,menuPane,contextMenu;
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

        arrowTypeComboBox.getItems().addAll(ArrowType.IMPLEMENTATION,ArrowType.INHERITANCE,ArrowType.ASSOCIATION,ArrowType.AGGREGATION,ArrowType.COMPOSITION,ArrowType.DEPENDANCY);
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
    public void addBox(BoxFacade b){
        BoxController box = new BoxController(b,variableEditor,methodEditor,this);
        this.getChildren().add(box);
        boxes.add(box);
        box.toggleCircleVisibility(!toggleOn);
    }

    public void addTestPoint (float x, float y)
    {
        TestPointController point = new TestPointController(x, y);
        this.getChildren().add(point);
    }

    public Point getMiddle(){
        return new Point(500,400);
    }

    //region arrowmaking

    private boolean makingArrow = false;
    private Arrow dragArrow;
    private BoxController arrowBox = null;
    private Point arrowStart;
    private boolean toggleOn = false;
    private List<Arrow> arrows = new ArrayList<>();


    @Override
    public void arrowEvent(Point p, BoxController box) {
        if(makingArrow) {
            this.getChildren().removeAll(dragArrow);

            //box == arrowBox => aborting arrowcreation
            if (box != arrowBox) {
                //todo make arrow in backend
                //todo get bends from backend
                //temporary
                List<Point> bends = new ArrayList<>();
                bends.add(new Point(p.x-50,arrowStart.y));
                bends.add(new Point(p.x-50,p.y));

                Arrow newArrow = new Arrow(arrowStart,p,bends);
                this.getChildren().addAll(newArrow);
                arrows.add(newArrow);
                newArrow.toBack();
            }
        }
        else{
            arrowBox=box;
            arrowStart=p;
            dragArrow = new Arrow(arrowStart,p,new ArrayList<>());
            this.getChildren().add(dragArrow);
        }
        toggleAnchorPoints();
        makingArrow=!makingArrow;
    }

    /**
     * toggles all anchorpoints on all classes
     */
    private void toggleAnchorPoints(){
        for(BoxController box:boxes){
            box.toggleCircleVisibility(toggleOn);
        }
        toggleOn=!toggleOn;
    }

    /**
     * updates the arrow when creating a arrow
     */
    @FXML
    private void dragArrow(MouseEvent e){
        if(makingArrow){
            this.getChildren().remove(dragArrow);
            dragArrow=new Arrow(arrowStart, new Point((int)e.getX(),(int)e.getY()),new ArrayList<>());
            this.getChildren().add(dragArrow);
            dragArrow.toBack();
        }
        e.consume();
    }

    //endregion

    //region Menus
    private Arrow clickedArrow = null;
    @FXML
    private void handleArrowMenu(MouseEvent e){
        Arrow closest = null;
        double min = 10000;
        for (Arrow a:arrows) {
            System.out.println(a.getDistaceFromClick(e) + " min = " + min);
            if(a.getDistaceFromClick(e)<min){
                min = a.getDistaceFromClick(e);
                closest = a;
            }
        }
        if(min<=15){
            clickedArrow = closest;
            arrowTypeComboBox.getSelectionModel().select(closest.getType());
            openArrowMenu(e.getX(),e.getY());
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
    private void handleContextMenu(ContextMenuEvent e){
        contextMenu.setLayoutX(e.getX());
        contextMenu.setLayoutY(e.getY());
        contextMenu.setVisible(true);
        menuPane.setVisible(true);
        menuPane.toFront();
        e.consume();
    }
    @FXML
    private void closeMenu(Event e){
        menuPane.setVisible(false);
        arrowMenu.setVisible(false);
        contextMenu.setVisible(false);
        menuPane.toBack();
        e.consume();
    }
    @FXML
    private void handleContextAddBox(MouseEvent e) {
        model.addBox(new Point((int) contextMenu.getLayoutX()-80,(int) contextMenu.getLayoutY()-35));
        closeMenu(e);
        e.consume();
    }

    @FXML
    private void deleteArrow(Event e){
        this.getChildren().remove(clickedArrow);
        arrows.remove(clickedArrow);
        closeMenu(e);
        e.consume();
    }
    @FXML
    private void changeArrow(Event e){
        clickedArrow.setType(arrowTypeComboBox.getValue());
        closeMenu(e);
        e.consume();
    }
    @FXML
    private void eventTrap(Event e){
        e.consume();
    }
    //endregion
}
