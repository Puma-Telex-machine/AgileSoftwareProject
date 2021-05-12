package frontend;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import model.point.Scale;
import model.point.ScaledPoint;
import model.relations.ArrowType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for holding and managing components of a arrow in the frontend
 */
public class Arrow extends AnchorPane{
    private AnchorPane head=new AnchorPane();
    private List<Line> lines=new ArrayList<>();
    private double startX,startY,endX,endY;
    private Line endline;
    private ArrowType type;
    private List<ScaledPoint> bends;

    public Arrow(Point start, Point end, List<ScaledPoint> bends) {//),double offsetX,double offsetY){

        this.startX = start.getX();
        this.startY = start.getY();
        this.endX = end.getX();
        this.endY = end.getY();
        this.bends = bends;
        endline = new Line();

        if(bends.size()>=1){
            //start line
            Line l1 = new Line();
            lines.add(l1);
            l1.setStartX(startX);
            l1.setStartY(startY);
            l1.setEndX(bends.get(0).getX(Scale.Frontend));
            l1.setEndY(bends.get(0).getY(Scale.Frontend));
            l1.setStroke(( new Color(0.72,0.72,0.72,1)));

            //all bends
            for(int i=1;i<bends.size();i++){
                Line l = new Line();
                lines.add(l);
                l.setStartX(bends.get(i-1).getX(Scale.Frontend));
                l.setStartY(bends.get(i-1).getY(Scale.Frontend));
                l.setEndX(bends.get(i).getX(Scale.Frontend));
                l.setEndY(bends.get(i).getY(Scale.Frontend));
                l.setStroke(( new Color(0.72,0.72,0.72,1)));
            }
            //end line
            lines.add(endline);
            endline.setStartX(bends.get(bends.size()-1).getX(Scale.Frontend));
            endline.setStartY(bends.get(bends.size()-1).getY(Scale.Frontend));
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
        if(!(start.getX()==end.getX()&&start.getY()==end.getY())) {
            setType(ArrowType.ASSOCIATION);

        }
    }

    /**
     * get the points for the different type of arrowheads
     * @param lineAngle the angle of the last line
     * @param endX end coordinate x
     * @param endY end coordinate y
     * @return   [arrow point1, arrow point2, point on line for rhoumbus, point on line inbetween arrowpoints]
     */
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

    /**
     * changes arrow type
     * @param type type to switch to
     */
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
        Point[] points = getArrowHeadPoints(lineAngle,endX,endY);

        Line l1 = new Line(endX,endY,points[0].x,points[0].y);
        l1.setStroke(( new Color(0.72,0.72,0.72,1)));
        head.getChildren().add(l1);
        Line l2 = new Line(endX,endY,points[1].x,points[1].y);
        l2.setStroke(( new Color(0.72,0.72,0.72,1)));
        head.getChildren().add(l2);
        Line l3 = new Line(endX,endY,points[3].x,points[3].y);
        l3.setStroke(( new Color(0.72,0.72,0.72,1)));
        head.getChildren().add(l3);

        endline.setEndX(points[3].x);
        endline.setEndY(points[3].y);

    }

    public List<ScaledPoint> getBends(){
        return bends;
    }

    public ArrowType getType() {
        return type;
    }

    /**
     * check if a click is on this arrow (only works if lines are horizontal and vertical)
     * @param event the click
     * @return  if click is on
     */
    public double getDistaceFromClick(MouseEvent event) {

        //todo make viable for all lines not just vertical and horizontal with formula
        // Distance = (| a*x1 + b*y1 + c |) / (sqrt( a*a + b*b)) line ax+bx+c=0  point (x1,y1)

        double x1 = event.getX();
        double y1 = event.getY();

        int diff = 10;

        double min = 100000;

        for (Line l:lines) {

            //vertical line
            if (l.getEndX() == l.getStartX()) {
                double minY = Math.min(l.getStartY(), l.getEndY());
                double maxY = Math.max(l.getStartY(), l.getEndY());
                double offsetX = Math.abs(l.getStartX() - x1);
                if (y1 > minY && y1 < maxY) {
                    min = Math.min(offsetX, min);
                } else if (y1 > maxY) {
                    //sqrt(a^2+b^2)
                    min = Math.min(Math.sqrt(Math.pow(offsetX, 2) + Math.pow(y1 - maxY, 2)), min);
                } else if (y1 < minY) {
                    //sqrt(a^2+b^2)
                    min = Math.min(Math.sqrt(Math.pow(offsetX, 2) + Math.pow(minY - y1, 2)), min);
                }
            }
            //Horizontal line
            else {
                if (l.getEndY() == l.getStartY()) {
                    double minX = Math.min(l.getStartX(), l.getEndX());
                    double maxX = Math.max(l.getStartX(), l.getEndX());
                    double offsetY = Math.abs(l.getStartY() - y1);
                    if (x1 > minX && x1 < maxX) {
                        min = Math.min(offsetY, min);
                    } else if (x1 > maxX) {
                        //sqrt(a^2+b^2)
                        min = Math.min(Math.sqrt(Math.pow(offsetY, 2) + Math.pow(x1 - maxX, 2)), min);
                    } else if (x1 < minX) {
                        //sqrt(a^2+b^2)
                        min = Math.min(Math.sqrt(Math.pow(offsetY, 2) + Math.pow(minX - x1, 2)), min);
                    }
                }
                //other line (does not work for horizontal or )
                else {
                    //line ax+bx+c=0  point (x1,y1)
                    // Distance = (| a*x1 + b*y1 + c |) / (sqrt( a*a + b*b))
                    double slope = (l.getEndY() - l.getStartY()) / (l.getEndX() - l.getStartX());
                    double a = slope;
                    double b = -1;
                    double c = l.getStartY() - slope * l.getStartX();
                    double minX = Math.min(l.getStartX(), l.getEndX());
                    double maxX = Math.max(l.getStartX(), l.getEndX());
                    double minY = Math.min(l.getStartY(), l.getEndY());
                    double maxY = Math.max(l.getStartY(), l.getEndY());


                    //if click is withing the rectangle between the startingpoints
                    if(minX<x1&&x1<maxX&&minY<y1&&y1<maxY) {
                        min = Math.min(Math.abs(a * x1 + b * y1 + c) / (Math.sqrt(a * a + b * b)), min);
                    }
                    //click is outside startingnode distance = distance to closest start/end node
                    else{
                        double dist1 = Math.sqrt(Math.pow(l.getStartX()-x1,2)+Math.pow(l.getStartY()-y1,2));
                        double dist2 = Math.sqrt(Math.pow(l.getEndX()-x1,2)+Math.pow(l.getEndY()-y1,2));
                        min = Math.min(Math.min(dist1,dist2),min);
                    }
                }
            }
        }
        return min;
    }
    // Function to find distance
    static double shortest_distance(double x1, double y1,
                                    double a, double b,
                                    double c)
    {
        double d = Math.abs(((a * x1 + b * y1 + c)) /
                (Math.sqrt(a * a + b * b)));
        return d;
    }
}
