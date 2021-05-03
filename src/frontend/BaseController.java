package frontend;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import model.facades.ModelFacade;
import model.Model;
import model.point.Scale;
import model.point.ScaledPoint;

import java.awt.*;


public class BaseController{

    @FXML
    AnchorPane UML;

    @FXML
    AnchorPane leftMenu;

    RecentController recent;

    CanvasController canvas;

    ShapeController shapes;

    OverviewController overview;

    ExercisesController exercises;



    public BaseController() {
        canvas = new CanvasController();
        model = ModelFacade.getModel();
        files = new FilesController(model.getFileHandler(), canvas);
        shapes = new ShapeController(canvas);
        overview = new OverviewController();
        exercises = new ExercisesController();
    }

    private void init(){
        leftMenue.getChildren().add(files);
        leftMenu.getChildren().add(shapes);
        leftMenu.getChildren().add(overview);
        leftMenu.getChildren().add(exercises);
        LockPane(files);
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
        AnchorPane.setBottomAnchor(pane, 0d);
        AnchorPane.setRightAnchor(pane,0d);
    }

    private void closeMenueTabbs()
    {
        files.setVisible(false);
        shapes.setVisible(false);
        overview.setVisible(false);
        exercises.setVisible(false);
        leftMenu.toBack();
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
        openMenuItem(files);
    }
    @FXML
    private void openShapes(){ openMenuItem(shapes); }

    @FXML
    private void  openOverview() { openMenuItem(overview); }

    @FXML
    private void openExercises() { openMenuItem(exercises);}

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
