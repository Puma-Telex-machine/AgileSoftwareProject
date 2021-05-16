package frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import model.ModelFacade;

import java.io.IOException;


public class BaseController extends AnchorPane{

    @FXML
    AnchorPane UML;

    @FXML
    AnchorPane leftMenu,minimize;
    @FXML
    AnchorPane contextMenu;

    ModelFacade model;

    FilesController files;

    CanvasController canvas;

    ShapeController shapes;

    OverviewController overview;

    ExercisesController exercises;



    public BaseController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("view/Base.fxml")));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        canvas = new CanvasController();
        model = ModelFacade.getModel();
        files = new FilesController(ModelFacade.getFileHandler(), canvas);
        shapes = new ShapeController(canvas);
        overview = new OverviewController();
        exercises = new ExercisesController();


        leftMenu.getChildren().add(files);
        leftMenu.getChildren().add(shapes);
        leftMenu.getChildren().add(overview);
        leftMenu.getChildren().add(exercises);
        LockPane(files);
        LockPane(shapes);
        LockPane(overview);
        LockPane(exercises);
        minimizeMenu();
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

    @FXML
    private void minimizeMenu()
    {
        files.setVisible(false);
        shapes.setVisible(false);
        overview.setVisible(false);
        exercises.setVisible(false);
        leftMenu.setVisible(false);
        leftMenu.toBack();
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
        boolean vis = menu.isVisible();
        minimizeMenu();
        if(vis){
            leftMenu.toBack();
        }
        else{
            leftMenu.toFront();
            leftMenu.setVisible(true);
            minimize.toFront();
            menu.setVisible(true);
        }
    }


}
