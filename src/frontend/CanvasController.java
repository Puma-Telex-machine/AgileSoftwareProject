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
    Line[] arrow= new Line[3];
    BoxController arrowBox = null;
    Point arrowStart;

    @Override
    public void arrowEvent(Point p, BoxController box) {
        if(makingArrow) {
            System.out.println("makingArrow");
            this.getChildren().removeAll(arrow);

            //box == arrowBox => aborting arrowcreation
            if (box != arrowBox) {
                //todo make arrow in backend
                Line[] add = drawArrowLine(arrowStart.x, arrowStart.y, p.x, p.y);
                this.getChildren().addAll(add);
                System.out.println("arrow created between " + arrowBox.getName() + " and " + box.getName());
                for (Line l:add) {
                    l.toBack();
                }
            }
        }
        else{
            System.out.println("not makingArrow");
            arrowBox=box;
            arrowStart=p;
        }
        toggleAnchorPoints();
        makingArrow=!makingArrow;
    }
    private void toggleAnchorPoints(){
        for(BoxController box:boxes){
            box.toggleCircleVisibility();
        }
    }

    @FXML
    private void dragArrow(MouseEvent e){
        if(makingArrow){
            this.getChildren().removeAll(arrow);
            arrow= drawArrowLine(arrowStart.getX(),arrowStart.getY(),e.getSceneX()-75,e.getSceneY());
            this.getChildren().addAll(arrow);
            for (Line l:arrow) {
                l.toBack();
            }
        }
        e.consume();
    }

    public Line[] drawArrowLine(double startX, double startY, double endX, double endY) {
        // get the slope of the line and find its angle
        double slope = (startY - endY) / (startX - endX);
        double lineAngle = Math.atan(slope);

        double arrowAngle = startX > endX ? Math.toRadians(30) : Math.toRadians(150);


        Line line = new Line(startX, startY, endX, endY);


        // create the arrow legs
        Line arrow1 = new Line();
        arrow1.setStartX(line.getEndX());
        arrow1.setStartY(line.getEndY());
        arrow1.setEndX(line.getEndX() + 10 * Math.cos(lineAngle - arrowAngle));
        arrow1.setEndY(line.getEndY() + 10 * Math.sin(lineAngle - arrowAngle));

        Line arrow2 = new Line();
        arrow2.setStartX(line.getEndX());
        arrow2.setStartY(line.getEndY());
        arrow2.setEndX(line.getEndX() + 10 * Math.cos(lineAngle + arrowAngle));
        arrow2.setEndY(line.getEndY() + 10 * Math.sin(lineAngle + arrowAngle));

        //Fix glitches
        if(startX==endX&&startY>endY){
            arrow2.setEndX(line.getEndX() + 10 * Math.cos(Math.toRadians(60)));
            arrow2.setEndY(line.getEndY() + 10 * Math.sin(Math.toRadians(60)));
            arrow1.setEndX(line.getEndX() + 10 * Math.cos(Math.toRadians(120)));
            arrow1.setEndY(line.getEndY() + 10 * Math.sin(Math.toRadians(120)));
        }
        if(startX==endX&&startY<endY){
            arrow2.setEndX(line.getEndX() + 10 * Math.cos(Math.toRadians(-60)));
            arrow2.setEndY(line.getEndY() + 10 * Math.sin(Math.toRadians(-60)));
            arrow1.setEndX(line.getEndX() + 10 * Math.cos(Math.toRadians(-120)));
            arrow1.setEndY(line.getEndY() + 10 * Math.sin(Math.toRadians(-120)));
        }

        line.setStroke(( new Color(0.72,0.72,0.72,1)));
        arrow1.setStroke(( new Color(0.72,0.72,0.72,1)));
        arrow2.setStroke(( new Color(0.72,0.72,0.72,1)));
        return new Line[]{line,arrow1,arrow2};
    }
    //endregion
}
