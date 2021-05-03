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
    }

    private void init(){
        UML.getChildren().add(recent);
        UML.getChildren().add(shapes);
        UML.getChildren().add(overview);
        UML.getChildren().add(exercises);
        LockPane(recent);
        LockPane(shapes);
        LockPane(overview);
        LockPane(exercises);
        closeMenueTabbs();
        UML.getChildren().add(canvas);
        LockPane(canvas);
        AnchorPane.setRightAnchor(canvas,0d);
    }

    private void LockPane(AnchorPane pane)
    {
        AnchorPane.setTopAnchor(pane, 0d);
        AnchorPane.setLeftAnchor(pane, 0d);
        AnchorPane.setBottomAnchor(pane, 0d);
    }

    private void closeMenueTabbs()
    {
        recent.setVisible(false);
        shapes.setVisible(false);
        overview.setVisible(false);
        exercises.setVisible(false);
        recent.toBack();
        shapes.toBack();
        overview.toBack();
        exercises.toBack();
    }


    //open Menus
    @FXML
    private void openRecent(){ openMenuItem(recent); }

    @FXML
    private void openShapes(){ openMenuItem(shapes); }

    @FXML
    private void  openOverview() { openMenuItem(overview); }

    @FXML
    private void openExercises() { openMenuItem(exercises);}

    private void openMenuItem(AnchorPane menu)
    {
        if(!UML.getChildren().contains(menu)) init();
        boolean vis = menu.isVisible();
        closeMenueTabbs();
        if(vis){
            menu.toBack();
        }
        else{
            menu.toFront();
            menu.setVisible(true);
        }
    }

}
