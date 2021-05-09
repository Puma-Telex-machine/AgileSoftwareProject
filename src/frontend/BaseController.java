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
    AnchorPane UML;

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
        UML.getChildren().add(recent);
        UML.getChildren().add(shapes);
        recent.setVisible(false);
        shapes.setVisible(false);
        UML.getChildren().add(canvas);
    }
    @FXML
    private void handleContextMenu(ContextMenuEvent e){
        context.setLayoutX(e.getX());
        context.setLayoutY(e.getY());
        contextMenu.toFront();
        e.consume();
    }
    @FXML
    private void exitContext(){
        UML.toFront();
    }
    @FXML
    private void handleContextAddBox(MouseEvent e) {
        model.addBox(new Point((int) context.getLayoutX()-80,(int) context.getLayoutY()-35));
        exitContext();
        e.consume();
    }

    //open Menus
    @FXML
    private void openRecent(){
        if(!UML.getChildren().contains(recent)) init();
        shapes.setVisible(false);
        recent.setVisible(!recent.isVisible());
        canvas.toBack();
    }
    @FXML
    private void openShapes(){
        if(!UML.getChildren().contains(shapes)) init();
        recent.setVisible(false);
        shapes.setVisible(!shapes.isVisible());
        canvas.toBack();
    }


    /*
    @FXML
    private void openComments(){
        comments.setVisible(!comments.isVisible());
    }
    @FXML
    private void openExecise(){
        exercise.setVisible(!exercise.isVisible());
    }
    @FXML
    private void openTextform(){
        textform.setVisible(!textform.isVisible());
    }*/
}
