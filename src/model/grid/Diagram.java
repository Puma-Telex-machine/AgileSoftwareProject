package model.grid;

import model.boxes.Box;
import model.facades.BoxFacade;
import model.facades.BoxManager;
import model.facades.RelationFacade;
import model.point.ScaledPoint;
import model.relations.ArrowType;
import model.relations.Relation;

import java.util.ArrayList;
import java.util.HashSet;

public class Diagram {

    BoxGrid boxGrid = new BoxGrid();
    RelationGrid relationGrid = new RelationGrid();
    AStar aStar = new AStar(boxGrid, relationGrid);
    HashSet<Relation> relations;

    public BoxFacade createBox(ScaledPoint position) {
        return new BoxManager(boxGrid, new Box("test", position));
    }

    public RelationFacade createRelation(BoxFacade from, BoxFacade to, ArrowType arrowType) {
        Relation relation = new Relation(from, to, arrowType);
        relations.add(relation);
        ArrayList<ScaledPoint> path = aStar.addRelation(relation);
        relationGrid.add(path, relation);
        return null; //TODO: hur gör vi här?
    }
}