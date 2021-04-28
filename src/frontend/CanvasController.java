package frontend;

import frontend.Observers.ArrowObserver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    }

    public Point getMiddle(){
        return new Point(500,400);
    }

    //region arrowmaking

    boolean makingArrow = false;
    Arrow arrow;
    BoxController arrowBox = null;
    Point arrowStart;

    @Override
    public void arrowEvent(Point p, BoxController box) {
        if(makingArrow) {
            this.getChildren().removeAll(arrow);

            //box == arrowBox => aborting arrowcreation
            if (box != arrowBox) {
                //todo make arrow in backend
                //todo get bends from backend
                //temporary
                List<Point> bends = new ArrayList<>();
                bends.add(new Point(p.x+20,arrowStart.y));
                bends.add(new Point(p.x+20,p.y));

                Arrow add = new Arrow(arrowStart,p,bends);
                this.getChildren().addAll(add);
                System.out.println("arrow created between " + arrowBox.getName() + " and " + box.getName());
                add.toBack();
            }
        }
        else{
            arrowBox=box;
            arrowStart=p;
            arrow = new Arrow(arrowStart,p,new ArrayList<>());
            this.getChildren().add(arrow);
        }
        toggleAnchorPoints();
        makingArrow=!makingArrow;
    }
    boolean toggleOn = false;
    private void toggleAnchorPoints(){
        for(BoxController box:boxes){
            box.toggleCircleVisibility(toggleOn);
        }
        toggleOn=!toggleOn;
    }

    @FXML
    private void dragArrow(MouseEvent e){
        if(makingArrow){
            this.getChildren().remove(arrow);
            arrow=new Arrow(arrowStart, new Point((int)e.getX(),(int)e.getY()),new ArrayList<>());
            this.getChildren().add(arrow);
            arrow.toBack();
        }
        e.consume();
    }

    //endregion
}
