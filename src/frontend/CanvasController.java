package frontend;

import frontend.Observers.ArrowObserver;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import model.Observer;
import model.facades.BoxFacade;
import model.relations.ArrowType;

import javax.swing.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class CanvasController extends AnchorPane implements Observer, ArrowObserver {

    VariableEditorController variableEditor;
    MethodEditorController methodEditor;

    @FXML
    private AnchorPane arrowMenu,arrowMenuPane;
    @FXML
    private ComboBox<ArrowType> arrowTypeComboBox;

    List<BoxController> boxes = new ArrayList<>();
    public CanvasController()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("view/Canvas.fxml")));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        arrowTypeComboBox.getItems().addAll(ArrowType.IMPLEMENTATION,ArrowType.INHERITANCE,ArrowType.ASSOCIATION,ArrowType.AGGREGATION,ArrowType.COMPOSITION,ArrowType.DEPENDANCY);
        arrowMenuPane.setVisible(false);

        variableEditor = new VariableEditorController();
        methodEditor = new MethodEditorController();
        this.getChildren().add(methodEditor);
        this.getChildren().add(variableEditor);
        variableEditor.setVisible(false);
        methodEditor.setVisible(false);
    }

    @Override
    public void addBox(BoxFacade b){
        BoxController box = new BoxController(b,variableEditor,methodEditor,this);
        this.getChildren().add(box);
        boxes.add(box);
        box.toggleCircleVisibility(!toggleOn);
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
    private Arrow clickedArrow = null;

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
                bends.add(new Point(100,100));
                bends.add(new Point(p.x-20,arrowStart.y));
                bends.add(new Point(p.x-20,p.y));

                Arrow newArrow = new Arrow(arrowStart,p,bends);
                this.getChildren().addAll(newArrow);

                newArrow.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(!newArrow.isClickOn(event)) return;
                        clickedArrow=newArrow;
                        arrowTypeComboBox.getSelectionModel().select(newArrow.getType());
                        openArrowMenu(event);
                    }

                    private void openArrowMenu(MouseEvent e) {
                        arrowMenuPane.setVisible(true);
                        arrowMenuPane.toFront();
                        arrowMenu.setLayoutX(e.getX());
                        arrowMenu.setLayoutY(e.getY());
                        e.consume();
                    }
                });
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

    @FXML
    private void deleteArrow(Event e){
        this.getChildren().remove(clickedArrow);
        minimizeArrowMenu(e);
    }
    @FXML
    private void changeArrow(Event e){
        clickedArrow.setType(arrowTypeComboBox.getValue());
        minimizeArrowMenu(e);
    }

    @FXML
    private void minimizeArrowMenu(Event e){
        arrowMenuPane.setVisible(false);
        arrowMenuPane.toBack();
        e.consume();
    }
    @FXML
    private void eventTrap(Event e){
        e.consume();
    }


    //endregion
}
