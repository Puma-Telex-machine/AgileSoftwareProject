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
    private AnchorPane head=new AnchorPane();
    private List<Line> lines=new ArrayList<>();
    private double startX,startY,endX,endY;
    private Line endline;
    private ArrowType type;
    private List<Point> bends;

    public Arrow(Point start,Point end,List<Point> bends){//),double offsetX,double offsetY){

        this.startX=start.getX();
        this.startY=start.getY();
        this.endX=end.getX();
        this.endY=end.getY();
        this.bends=bends;
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

        //if start=end glitches occur for head
        if(!(start.x==end.x&&start.y==end.y)) {
            setType(ArrowType.INHERITANCE);
        }
    }

    private Point[] getArrowHeadPoints(double lineAngle, double endX, double endY) {

        double arrowAngle = endline.getStartX() > endline.getEndX() ? Math.toRadians(30) : -Math.toRadians(150);

        if(endline.getStartX()==endline.getEndX()){
            arrowAngle= Math.toRadians(30);
        }

        Point p1 = new Point((int)(endX + 10 * Math.cos(lineAngle - arrowAngle)),(int)(endY + 10 * Math.sin(lineAngle - arrowAngle)));
        Point p2 = new Point((int)(endX + 10 * Math.cos(lineAngle + arrowAngle)),(int)(endY + 10 * Math.sin(lineAngle + arrowAngle)));
        Point p3,p4;
        double vx = 5*Math.sqrt(3) * Math.cos(lineAngle);
        double vy = 5*Math.sqrt(3) * Math.sin(lineAngle);

        if(endline.getStartX()>=endX){
            p4 = new Point((int)(endX + vx),(int)(endY + vy));
            p3= new Point((int)(endX + 2*vx),(int)(endY + 2*vy));
        }
        else{
            p4 = new Point((int)(endX - vx),(int)(endY - vy));
            p3= new Point((int)(endX - 2*vx),(int)(endY - 2*vy));
        }

        return new Point[]{p1,p2,p3,p4};
    }

    public void setType(ArrowType type){

        if(this.type==type) return;
        this.type=type;

        double lineAngle = Math.atan((endline.getStartY() - endline.getEndY()) / (endline.getStartX() - endline.getEndX()));

        switch (type){
            case IMPLEMENTATION:
                setDottedLine();
                setTriangleHead(false,lineAngle);
                break;

            case INHERITANCE:
                setFullLine();
                setTriangleHead(false,lineAngle);
                break;

            case DEPENDANCY:
                setDottedLine();
                setArrowHead(lineAngle);
                break;
            case ASSOCIATION:
                setFullLine();
                setArrowHead(lineAngle);
                break;
            case AGGREGATION:
                setFullLine();
                setRhoumbusHead(false,lineAngle);
                break;
            case COMPOSITION:
                setFullLine();
                setRhoumbusHead(true,lineAngle);
                break;
        }
    }

    private void setFullLine(){
        for (Line l:lines) {
            l.getStrokeDashArray().clear();
        }
    }
    private void setDottedLine(){
        for (Line l:lines) {
            l.getStrokeDashArray().addAll(10d, 10d);
        }
    }
    private void setTriangleHead(boolean fill,double lineAngle){
        Point[] points=getArrowHeadPoints(lineAngle,endX,endY);
        Polygon triangle = new Polygon(
                endX, endY,
                points[0].getX(), points[0].getY(),
                points[1].getX(), points[1].getY()
        );
        if(!fill){
            endline.setEndX(points[3].x);
            endline.setEndY(points[3].y);
        }
        setPolygonHead(triangle,fill,lineAngle);
    }

    private void setRhoumbusHead(boolean fill,double lineAngle){
        Point[] points=getArrowHeadPoints(lineAngle,endX,endY);
        Polygon rhoumbus = new Polygon(
                endX, endY,
                points[0].getX(), points[0].getY(),
                points[2].getX(),points[2].getY(),
                points[1].getX(), points[1].getY()
        );
        if(!fill){
            endline.setEndX(points[2].x);
            endline.setEndY(points[2].y);
        }
        setPolygonHead(rhoumbus,fill,lineAngle);
    }

    private void setPolygonHead(Polygon polygon,boolean fill,double lineAngle){
        head.getChildren().clear();

        if(fill) polygon.setFill(new Color(0.72,0.72,0.72,1));
        else {
            polygon.setFill(new Color(0,0,0,0));
            polygon.setStroke(new Color(0.72,0.72,0.72,1));
            polygon.setStrokeWidth(1);
        }
        head.getChildren().add(polygon);
        polygon.toFront();
        head.toFront();
    }

    private void setArrowHead(double lineAngle){
        head.getChildren().clear();

        for (Point point:getArrowHeadPoints(lineAngle,endX,endY)) {
            Line l = new Line(endX,endY,point.x,point.y);
            l.setStroke(( new Color(0.72,0.72,0.72,1)));
            head.getChildren().add(l);
        }
    }

    public List<Point> getBends(){
        return bends;
    }

    public ArrowType getType() {
        return type;
    }
}
