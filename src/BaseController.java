import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Model;
import model.Observer;
import model.boxes.Box;
import model.facades.BoxFacade;

import java.awt.*;


public class BaseController implements Observer{

    @FXML
    private VBox context;

    @FXML
    AnchorPane UML;
    @FXML
    AnchorPane contextMenu;

    Model model;

    VariableEditorController variableEditor;

    MethodEditorController methodEditor;

    Left_menuController menu;

    RecentController recent;



    public BaseController() {

        methodEditor = new MethodEditorController();
        variableEditor = new VariableEditorController();
        model = Model.getModel();
        model.addObserver(this);
    }

    private void init(){
        UML.getChildren().add(methodEditor);
        UML.getChildren().add(variableEditor);
        methodEditor.setVisible(false);
        variableEditor.setVisible(false);
    }

    @FXML
    private void handleAddBox(Event e) {
        if(!UML.getChildren().contains(variableEditor)) init();
        model.addBox(new Point(0, 0));
        e.consume();
    }

    @Override
    public void addBox(BoxFacade b){
        BoxController box = new BoxController(b,variableEditor,methodEditor);
        UML.getChildren().add(box);
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
        if(UML.getChildren().contains(recent)){
            
        }
    }
}
