package frontend;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import model.Observer;
import model.facades.BoxFacade;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CanvasController extends AnchorPane implements Observer,ArrowObserver{

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

    //arrowmaking

    boolean makingArrow = false;
    BoxController arrowBox = null;
    Point2D arrowStart;

    @Override
    public void arrowEvent(Point p, BoxController box) {
        if(makingArrow) {
            //aborting arrowcreation
            if (box != arrowBox) {
            //todo make arrow in backend
            drawArrowLine(arrowStart.getX(), arrowStart.getY(), p.x-75, p.y);
            System.out.println("arrow created between " + arrowBox.getName() + " and " + box.getName());
            }
        }
        else{
            arrowBox=box;
            System.out.println("starting arrow");
            arrowStart=new Point(p.x-75,p.y);
        }
        toggleAnchorPoints();
        makingArrow=!makingArrow;
    }
    private void toggleAnchorPoints(){
        for(BoxController box:boxes){
            box.toggleCircleVisibility();
        }
    }

    public void drawArrowLine(double startX, double startY, double endX, double endY) {
        System.out.println("start " + startX + " " + startY + " to " + endX + " " +endY);
        // get the slope of the line and find its angle
        double slope = (startY - endY) / (startX - endX);
        double lineAngle = Math.atan(slope);

        double arrowAngle = startX > endX ? Math.toRadians(45) : -Math.toRadians(225);

        Line line = new Line(startX, startY, endX, endY);

        double lineLength = Math.sqrt(Math.pow(startX - endX, 2) + Math.pow(startY - endY, 2));
        double arrowLength = lineLength / 10;

        // create the arrow legs
        Line arrow1 = new Line();
        arrow1.setStartX(line.getEndX());
        arrow1.setStartY(line.getEndY());
        arrow1.setEndX(line.getEndX() + arrowLength * Math.cos(lineAngle - arrowAngle));
        arrow1.setEndY(line.getEndY() + arrowLength * Math.sin(lineAngle - arrowAngle));

        Line arrow2 = new Line();
        arrow2.setStartX(line.getEndX());
        arrow2.setStartY(line.getEndY());
        arrow2.setEndX(line.getEndX() + arrowLength * Math.cos(lineAngle + arrowAngle));
        arrow2.setEndY(line.getEndY() + arrowLength * Math.sin(lineAngle + arrowAngle));

        this.getChildren().addAll(line, arrow1, arrow2);
    }
}
