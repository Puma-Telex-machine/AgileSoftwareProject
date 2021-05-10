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
import model.boxes.Box;
import model.boxes.BoxType;
import model.facades.Observer;
import model.facades.BoxFacade;
import model.facades.RelationFacade;
import model.point.Scale;
import model.point.ScaledPoint;
import model.relations.ArrowType;
import model.relations.Relation;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CanvasController extends AnchorPane implements Observer, ArrowObserver {

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


    @Override
    public void arrowEvent(Point p, BoxController box) {
        //attach arrow
        if (makingArrow) {
            this.getChildren().removeAll(dragArrow);
            //box == arrowBox => aborting arrowcreation
            if (box != arrowBox) {

                ScaledPoint offsetFrom = new ScaledPoint(Scale.Frontend, (int) (p.x - box.getLayoutX()), (int) (p.y - box.getLayoutY()));
                ScaledPoint offsetTo = new ScaledPoint(Scale.Frontend, (int) (arrowStart.x - arrowBox.getLayoutX()), (arrowStart.y - arrowBox.getLayoutY()));

                model.addRelation(arrowBox.getBox(), offsetFrom, box.getBox(), offsetTo, ArrowType.ASSOCIATION);
            }
        }
        //start making arrow
        else {
            arrowBox = box;
            arrowStart = new Point(p.x, p.y);
            dragArrow = new Arrow(arrowStart, new Point(p.x, p.y), new ArrayList<>());
            this.getChildren().add(dragArrow);
        }
        toggleAnchorPoints();
        makingArrow = !makingArrow;
    }

    @Override
    public void addRelation(RelationFacade relation) {
        List<ScaledPoint> bends = relation.getPath();
        ScaledPoint last = bends.get(bends.size() - 1);

        Arrow newArrow = new Arrow(arrowStart, new Point(last.getX(Scale.Frontend), last.getY(Scale.Frontend)), bends);
        newArrow.setType(relation.getArrowType());

        this.getChildren().addAll(newArrow);
        newArrow.toBack();
        arrowMap.put(newArrow, relation);
        arrows.add(newArrow);
    }

    @Override
    public void boxDrag(BoxFacade box, Point offset) {
        List<Relation> relations = model.getRelationStart(box);
        for (Relation r:relations) {
            Arrow arrow = arrowMap.get(r);
            this.getChildren().remove(arrow);
            arrows.remove(arrow);
            arrowMap.remove(r);
            relationMap.remove(arrow);

            Point start = new Point((int) arrow.getStart().x+offset.x,(int) arrow.getStart().y+offset.y);

            addArrow(box,r.getTo(),start,arrow.getEnd(),r);
        }
        relations = model.getRelationEnd(box);
        for (Relation r:relations) {
            Arrow arrow = arrowMap.get(r);
            this.getChildren().remove(arrow);
            arrows.remove(arrow);
            arrowMap.remove(r);
            relationMap.remove(arrow);

            Point end = new Point((int) arrow.getEnd().x+offset.x,(int) arrow.getEnd().y+offset.y);

            addArrow(box,r.getTo(),arrow.getStart(),end,r);
        }
    }

    private void addArrow(BoxFacade from, BoxFacade to,Point start, Point end,Relation relation){
        List<Point> bends = model.getArrowBends(from,to);
        //temporary
        bends.add(new Point(end.x-50,start.y));
        bends.add(new Point(end.x-50,end.y));

        Arrow newArrow = new Arrow(start,end,bends);
        newArrow.setType(relation.getArrowType());

        this.getChildren().addAll(newArrow);
        newArrow.toBack();
        arrowMap.put(relation,newArrow);
        relationMap.put(newArrow,relation);
        arrows.add(newArrow);
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
            dragArrow = new Arrow(arrowStart, new Point((int) e.getX(), (int) e.getY()), new ArrayList<>());
            this.getChildren().add(dragArrow);
            dragArrow.toBack();
        }
        e.consume();
    }

    //endregion

    //region Menus
    private Arrow clickedArrow = null;

    @FXML
    private void handleArrowMenu(MouseEvent e) {
        if (makingArrow) {
            makingArrow = false;
            this.getChildren().remove(dragArrow);
            e.consume();
            return;
        }
        Arrow closest = null;
        double min = 10000;
        for (Arrow a : arrows) {
            double distance = a.getDistaceFromClick(e);
            if (distance < min) {
                min = distance;
                closest = a;
            }
        }
        if (min <= 15) {
            clickedArrow = closest;
            arrowTypeComboBox.getSelectionModel().select(closest.getType());
            openArrowMenu(e.getX(), e.getY());
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
            makingArrow = false;
            this.getChildren().remove(dragArrow);
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
        this.getChildren().remove(clickedArrow);
        arrows.remove(clickedArrow);
        closeMenu(e);
        e.consume();
    }

    @FXML
    private void changeArrow(Event e) {
        ArrowType type = arrowTypeComboBox.getValue();
        clickedArrow.setType(type);
        arrowMap.get(clickedArrow).changeRelation(type);
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
            boxes.get(i).setVisible(false); //todo: Properly remove items here
        }
        boxes = new ArrayList<>();
    }
}
