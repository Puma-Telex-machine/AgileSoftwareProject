package frontend;

import javafx.fxml.FXML;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Model;
import model.point.Scale;
import model.point.ScaledPoint;

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

    OverviewController overview;



    public BaseController() {
        recent = new RecentController();
        canvas = new CanvasController();
        shapes = new ShapeController(canvas);
        overview = new OverviewController();
        model = Model.getModel();
        model.addObserver(canvas);
    }

    private void init(){
        leftMenue.getChildren().add(recent);
        leftMenue.getChildren().add(shapes);
        leftMenue.getChildren().add(overview);
        LockPane(recent);
        LockPane(shapes);
        LockPane(overview);
        closeMenueTabbs();
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

    private void closeMenueTabbs()
    {
        recent.setVisible(false);
        shapes.setVisible(false);
        overview.setVisible(false);
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
        ScaledPoint newBoxPosition = new ScaledPoint(Scale.Frontend, contextMenu.getLayoutX()-80, contextMenu.getLayoutY()-35);
        model.addBox(newBoxPosition);
        exitContext();
        e.consume();
    }

    //open Menus
    @FXML
    private void openRecent(){
        openMenuItem(recent);
    }
    @FXML
    private void openShapes(){
        openMenuItem(shapes);
    }

    @FXML
    private void  openOverview()
    {
        openMenuItem(overview);
    }

    private void openMenuItem(AnchorPane menu)
    {
        boolean vis = menu.isVisible();
        if(!leftMenue.getChildren().contains(menu)) init();
        closeMenueTabbs();
        menu.setVisible(!vis);
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
