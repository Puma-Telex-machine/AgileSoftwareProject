package frontend;

import javafx.fxml.FXML;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Model;

import java.awt.*;


public class BaseController{

    @FXML
    AnchorPane UML;

    @FXML
    AnchorPane leftMenue;

    @FXML
    AnchorPane contextMenu;

    Model model;

    RecentController recent;

    CanvasController canvas;

    ShapeController shapes;



    public BaseController() {
        recent = new RecentController();
        canvas = new CanvasController();
        shapes = new ShapeController(canvas);
        model = Model.getModel();
        model.addObserver(canvas);
    }

    private void init(){
        leftMenue.getChildren().add(recent);
        leftMenue.getChildren().add(shapes);
        LockPane(recent);
        LockPane(shapes);
        recent.setVisible(false);
        shapes.setVisible(false);
        UML.getChildren().add(canvas);
        LockPane(canvas);
    }

    private void LockPane(AnchorPane pane)
    {
        AnchorPane.setTopAnchor(pane, 0d);
        AnchorPane.setLeftAnchor(pane, 0d);
        AnchorPane.setRightAnchor(pane, 0d);
        AnchorPane.setBottomAnchor(pane, 0d);
    }

    @FXML
    private void handleContextMenu(ContextMenuEvent e){
        contextMenu.setLayoutX(e.getX());
        contextMenu.setLayoutY(e.getY());
        contextMenu.setVisible(true);
        e.consume();
    }
    @FXML
    private void exitContext(){
        contextMenu.setVisible(false);
    }
    @FXML
    private void handleContextAddBox(MouseEvent e) {
        model.addBox(new Point((int) contextMenu.getLayoutX()-80,(int) contextMenu.getLayoutY()-35));
        exitContext();
        e.consume();
    }

    //open Menus
    @FXML
    private void openRecent(){
        if(!leftMenue.getChildren().contains(recent)) init();
        shapes.setVisible(false);
        recent.setVisible(!recent.isVisible());
        canvas.toBack();
    }
    @FXML
    private void openShapes(){
        if(!leftMenue.getChildren().contains(shapes)) init();
        recent.setVisible(false);
        shapes.setVisible(!shapes.isVisible());
        canvas.toBack();
    }
    /*
    @FXML
    private void openRecent(){
        recent.setVisible(!recent.isVisible());
    }
    @FXML
    private void openRecent(){
        recent.setVisible(!recent.isVisible());
    }
    @FXML
    private void openRecent(){
        recent.setVisible(!recent.isVisible());
    }*/
}
