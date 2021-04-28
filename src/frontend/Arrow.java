package frontend;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import model.relations.ArrowType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Arrow extends AnchorPane{
    AnchorPane head=new AnchorPane();
    List<Line> lines=new ArrayList<>();
    double startX,startY,endX,endY;
    public Arrow(double startX,double startY,double endX,double endY){//),double offsetX,double offsetY){

        this.startX=startX;
        this.startY=startY;
        this.endX=endX;
        this.endY=endY;

        Line l1 = new Line();
        lines.add(l1);

        Line l2 = new Line();
        lines.add(l2);

        this.getChildren().addAll(lines);
        this.getChildren().add(head);

        //todo make better
        l1.setStartX(startX);
        l1.setStartY(startY);
        l1.setEndX(startX);
        l1.setEndY(endY);
        l2.setStartX(startX);
        l2.setStartY(endY);
        l2.setEndX(endX);
        l2.setEndY(endY);
        l1.setStroke(( new Color(0.72,0.72,0.72,1)));
        l2.setStroke(( new Color(0.72,0.72,0.72,1)));

        setType(ArrowType.EXTENDS);
    }
    public void setType(ArrowType type){
        double lineAngle=0;
        if(startX<endX) lineAngle=Math.toRadians(180);
        head.getChildren().clear();

        switch (type){
            case IMPLEMENTS:
                //line
                for (Line l:lines) {
                    l.getStrokeDashArray().addAll(10d, 10d);
                }
                //head
                Point[] points=getLineHeadPoints(lineAngle,endX,endY);
                Polygon triangle = new Polygon(
                        endX, endY,
                        points[0].getX(), points[0].getY(),
                        points[1].getX(), points[1].getY()
                );
                triangle.setFill(new Color(0.72,0.72,0.72,1));
                head.getChildren().add(triangle);
                triangle.toFront();
                head.toFront();
                break;

            case EXTENDS:
                //line
                for (Line l:lines) {
                    l.getStrokeDashArray().clear();
                }

                //head
                for (Point point:getLineHeadPoints(lineAngle,endX,endY)) {
                    Line l = new Line(endX,endY,point.x,point.y);
                    l.setStroke(( new Color(0.72,0.72,0.72,1)));
                    head.getChildren().add(l);
                }
                break;
        }
    }


    public Point[] getLineHeadPoints(double lineAngle, double endX, double endY) {

        double arrowAngle = lineAngle < 180 ? Math.toRadians(30) : Math.toRadians(150);

        Point p1 = new Point((int)(endX + 10 * Math.cos(lineAngle - arrowAngle)),(int)(endY + 10 * Math.sin(lineAngle - arrowAngle)));
        Point p2 = new Point((int)(endX + 10 * Math.cos(lineAngle + arrowAngle)),(int)(endY + 10 * Math.sin(lineAngle + arrowAngle)));

        return new Point[]{p1,p2};
    }
}
