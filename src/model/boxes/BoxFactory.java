package model.boxes;

import model.grid.IDiagram;
import model.point.ScaledPoint;

public class BoxFactory {
    IDiagram diagram;

    public BoxFactory(IDiagram diagram) {
        this.diagram = diagram;
    }

    Box createBox(ScaledPoint position, BoxType boxType) {
        String defaultName;
        switch (boxType) {
            case CLASS -> defaultName = "Class";
            case ABSTRACTCLASS -> defaultName =  "Abstract Class";
            case INTERFACE -> defaultName = "Interface";
            default -> defaultName = "Box";
        }
        return new Box(diagram, defaultName, position, boxType);
    }
}
