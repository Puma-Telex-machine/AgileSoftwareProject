package frontend.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.ModelFacade;

import java.io.IOException;

public class BaseController extends AnchorPane {

    @FXML
    AnchorPane UML;

    @FXML
    AnchorPane leftMenu, minimize;
    @FXML
    AnchorPane contextMenu;

    ModelFacade model;

    FilesController files;

    CanvasController canvas;

    ShapeController shapes;

    OverviewController overview;

    ExercisesController exercises;

    TemplatesController templates;

    public BaseController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("../view/Base.fxml")));
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
        templates = new TemplatesController(ModelFacade.getFileHandler(), canvas);

        leftMenu.getChildren().add(files);
        leftMenu.getChildren().add(shapes);
        leftMenu.getChildren().add(overview);
        leftMenu.getChildren().add(exercises);
        leftMenu.getChildren().add(templates);
        LockPane(files);
        LockPane(shapes);
        LockPane(overview);
        LockPane(exercises);
        LockPane(templates);
        minimizeMenu();
        UML.getChildren().add(canvas);
        LockPane(canvas);

    }

    private void LockPane(AnchorPane pane) {
        AnchorPane.setTopAnchor(pane, 0d);
        AnchorPane.setLeftAnchor(pane, 0d);
        AnchorPane.setBottomAnchor(pane, 0d);
        AnchorPane.setRightAnchor(pane, 0d);
    }

    @FXML
    private void minimizeMenu() {
        files.setVisible(false);
        shapes.setVisible(false);
        overview.setVisible(false);
        exercises.setVisible(false);
        leftMenu.setVisible(false);
        templates.setVisible(false);
        leftMenu.toBack();
    }

    // open Menus

    @FXML
    private void openFiles() {
        openMenuItem(files);
    }

    @FXML
    private void openShapes() {
        openMenuItem(shapes);
    }

    @FXML
    private void openOverview() {
        openMenuItem(overview);
    }

    @FXML
    private void openExercises() {
        openMenuItem(exercises);
    }

    @FXML
    private void openTemplates(){ openMenuItem(templates); }

    /**
     * Opens a left side menu
     * 
     * @param menu The anchor pane base for the menu
     */
    private void openMenuItem(AnchorPane menu) {
        boolean vis = menu.isVisible();
        minimizeMenu();
        if (vis) {
            leftMenu.toBack();
        } else {
            leftMenu.toFront();
            leftMenu.setVisible(true);
            minimize.toFront();
            menu.setVisible(true);
        }
    }

    /**
     * Is called when the user presses a key
     * 
     * @param e The key-pressed event
     */
    public void onKeyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case SHIFT:
                canvas.startAddSelect();
                break;
            case DELETE:
                canvas.deleteSelectedBoxes();
                break;
        }
        e.consume();
    }
    @FXML
    private void undo(){
        if(model.canUndo()) {
            model.stopUndo();
            canvas.clearArrows();
            canvas.clearBoxes();
            model.resumeUndo();
            model.loadUndoLayer();
        }
    }

    @FXML
    private void redo(){
        if(model.canRedo()) {
            model.stopUndo();
            canvas.clearArrows();
            canvas.clearBoxes();
            model.resumeUndo();
            model.loadRedoLayer();
        }
    }

    /**
     * Is called when the user releases a key
     * 
     * @param e The key-release event
     */
    public void onKeyReleased(KeyEvent e) {
        switch (e.getCode()) {
            case SHIFT:
                canvas.endAddSelect();
                break;
        }
        e.consume();
    }
}
