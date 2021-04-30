package frontend;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import model.Model;
import model.Observer;
import model.facades.BoxFacade;
import model.grid.BoxGrid;
import model.grid.RelationGrid;
import model.grid.Scaler;
import model.relations.ArrowType;
import model.relations.Relation;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class CanvasController extends AnchorPane implements Observer {

    VariableEditorController variableEditor;
    MethodEditorController methodEditor;
    public CanvasController()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("view/Canvas.fxml")));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        variableEditor = new VariableEditorController();
        methodEditor = new MethodEditorController();
        this.getChildren().add(methodEditor);
        this.getChildren().add(variableEditor);
        variableEditor.setVisible(false);
        methodEditor.setVisible(false);
    }

    Model model;

    private void test() {
        model = Model.getModel();
        RelationGrid relationGrid = new RelationGrid(model.getBoxGrid(), 3, 4);
        ArrayList<Point> path = relationGrid.addRelation(new Relation(boxFacades.get(0).getBox(), boxFacades.get(1).getBox(), ArrowType.EXTENDS));
        for (Point p : path) {
            addTestPoint(p);
        }
    }

    ArrayList<BoxFacade> boxFacades = new ArrayList<>();

    @Override
    public void addBox(BoxFacade b){
        addTestPoint(new Point(10, 10));
        boxFacades.add(b);
        BoxController box = new BoxController(b,variableEditor,methodEditor);
        this.getChildren().add(box);
        if (boxFacades.size() >= 2) {
            test();
        }
    }

    public void addTestPoint (Point p)
    {
        p = Scaler.convertFromScale(p);
        TestPointController point = new TestPointController(p.x, p.y);
        this.getChildren().add(point);
    }

    public Point getMiddle(){
        return new Point(500,400);
    }

}
