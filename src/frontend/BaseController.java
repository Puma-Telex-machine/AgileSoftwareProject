package frontend;

import javafx.fxml.FXML;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Model;
import model.facades.BoxFacade;

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

    ExercisesController exercises;



    public BaseController() {
        recent = new RecentController();
        canvas = new CanvasController();
        shapes = new ShapeController(canvas);
        overview = new OverviewController();
        exercises = new ExercisesController();
        model = Model.getModel();
        model.addObserver(canvas);
    }

    private void init(){
        leftMenue.getChildren().add(recent);
        leftMenue.getChildren().add(shapes);
        leftMenue.getChildren().add(overview);
        leftMenue.getChildren().add(exercises);
        LockPane(recent);
        LockPane(shapes);
        LockPane(overview);
        LockPane(exercises);
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
        exercises.setVisible(false);
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

    @FXML
    private void openExercises() { openMenuItem(exercises);}

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
