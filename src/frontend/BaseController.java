package frontend;

import javafx.fxml.FXML;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Model;
import model.facades.BoxFacade;

import java.awt.*;


public class BaseController{

    @FXML
    private VBox context;

    @FXML
    AnchorPane leftMenu;

    RecentController recent;

    CanvasController canvas;

    ShapeController shapes;



    public BaseController() {
        recent = new RecentController();
        canvas = new CanvasController();
        shapes = new ShapeController(canvas);
        //overview = new OverviewController();
        //exercises = new ExercisesController();
    }

    private void init(){
        leftMenu.getChildren().add(recent);
        leftMenu.getChildren().add(shapes);
        //leftMenu.getChildren().add(overview);
        //leftMenu.getChildren().add(exercises);
        LockPane(recent);
        LockPane(shapes);
        //LockPane(overview);
        //LockPane(exercises);
        closeMenueTabbs();
        //UML.getChildren().add(canvas);
        LockPane(canvas);

    }

    private void LockPane(AnchorPane pane)
    {
        AnchorPane.setTopAnchor(pane, 0d);
        AnchorPane.setLeftAnchor(pane, 0d);
        AnchorPane.setBottomAnchor(pane, 0d);
        AnchorPane.setRightAnchor(pane,0d);
    }

    private void closeMenueTabbs()
    {
        recent.setVisible(false);
        shapes.setVisible(false);
        //overview.setVisible(false);
        //exercises.setVisible(false);
        leftMenu.toBack();
    }


    //open Menus
    @FXML
    private void openRecent(){ openMenuItem(recent); }

    @FXML
    private void openShapes(){ openMenuItem(shapes); }

    /*@FXML
    private void  openOverview() { openMenuItem(overview); }

    @FXML
    private void openExercises() { openMenuItem(exercises);}*/

    private void openMenuItem(AnchorPane menu)
    {
        if(!leftMenu.getChildren().contains(menu)) init();
        boolean vis = menu.isVisible();
        closeMenueTabbs();
        if(vis){
            leftMenu.toBack();
        }
        else{
            leftMenu.toFront();
            menu.setVisible(true);
        }
    }

}
