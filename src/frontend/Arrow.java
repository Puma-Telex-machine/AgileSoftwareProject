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
    Line endline;
    ArrowType type;
    public Arrow(Point start,Point end,List<Point> bends){//),double offsetX,double offsetY){

        this.startX=start.getX();
        this.startY=start.getY();
        this.endX=end.getX();
        this.endY=end.getY();

        endline = new Line();
        if(bends.size()>=1){
            //start line
            Line l1 = new Line();
            lines.add(l1);
            l1.setStartX(startX);
            l1.setStartY(startY);
            l1.setEndX(bends.get(0).getX());
            l1.setEndY(bends.get(0).getY());
            l1.setStroke(( new Color(0.72,0.72,0.72,1)));

            //all bends
            for(int i=1;i<bends.size();i++){
                Line l = new Line();
                lines.add(l);
                l.setStartX(bends.get(i-1).getX());
                l.setStartY(bends.get(i-1).getY());
                l.setEndX(bends.get(i).getX());
                l.setEndY(bends.get(i).getY());
                l.setStroke(( new Color(0.72,0.72,0.72,1)));
            }
            //end line
            lines.add(endline);
            endline.setStartX(bends.get(bends.size()-1).getX());
            endline.setStartY(bends.get(bends.size()-1).getY());
            endline.setEndX(endX);
            endline.setEndY(endY);
            endline.setStroke(( new Color(0.72,0.72,0.72,1)));
        }
        else {
            lines.add(endline);
            endline.setStartX(startX);
            endline.setStartY(startY);
            endline.setEndX(endX);
            endline.setEndY(endY);
            endline.setStroke(( new Color(0.72,0.72,0.72,1)));
        }

        this.getChildren().addAll(lines);
        this.getChildren().add(head);
        setType(ArrowType.EXTENDS);
    }
    public void setType(ArrowType type){

        if(this.type==type) return;

        this.type=type;

        double slope = (endline.getStartY() - endline.getEndY()) / (endline.getStartX() - endline.getEndX());
        double lineAngle = Math.atan(slope);

        head.getChildren().clear();

        switch (type){
            case IMPLEMENTS:
                //line
                for (Line l:lines) {
                    l.getStrokeDashArray().addAll(10d, 10d);
                }
                //head
                Point[] points=getArrowHeadPoints(lineAngle,endX,endY);
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
                for (Point point:getArrowHeadPoints(lineAngle,endX,endY)) {
                    Line l = new Line(endX,endY,point.x,point.y);
                    l.setStroke(( new Color(0.72,0.72,0.72,1)));
                    head.getChildren().add(l);
                }
                break;
        }
    }


    private Point[] getArrowHeadPoints(double lineAngle, double endX, double endY) {

        double arrowAngle = endline.getStartX() > endline.getEndX() ? Math.toRadians(30) : -Math.toRadians(150);

        if(endline.getStartX()==endline.getEndX()){
            arrowAngle= Math.toRadians(30);
        }

        Point p1 = new Point((int)(endX + 10 * Math.cos(lineAngle - arrowAngle)),(int)(endY + 10 * Math.sin(lineAngle - arrowAngle)));
        Point p2 = new Point((int)(endX + 10 * Math.cos(lineAngle + arrowAngle)),(int)(endY + 10 * Math.sin(lineAngle + arrowAngle)));

        return new Point[]{p1,p2};
    }
}
