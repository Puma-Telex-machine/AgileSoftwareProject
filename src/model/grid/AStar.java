package model.grid;

import model.point.Scale;
import model.point.ScaledPoint;
import model.relations.Relation;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class AStar {

    BoxGridView boxGrid;
    RelationGrid relationGrid;

    TreeMap<ScaledPoint, PathNode> visited = new TreeMap<>();
    PriorityQueue<PathNode> discovered = new PriorityQueue<>();

    int stepCost = 1;
    int bendCost = 10;
    int crossCost = 100;
    ScaledPoint destination;

    public AStar(BoxGridView boxGrid, RelationGrid relationGrid) {
        this.boxGrid = boxGrid;
        this.relationGrid = relationGrid;
    }

    public PathNode findPath(Relation relation) {
        destination = relation.getToPosition();

        PathNode startNode = new PathNode(this, relation);
        startNode.position = relation.getFromPosition();
        startNode.cost = 0;
        discovered.add(startNode);

        while (!discovered.isEmpty()) {
            PathNode current = discovered.remove();

            if (current.position.equals(destination)) return current;

            visited.put(current.position, current);

            discover(current, Direction.UP);
            discover(current, Direction.DOWN);
            discover(current, Direction.LEFT);
            discover(current, Direction.RIGHT);
        }
        return null;
    }

/*
    private ArrayList<ScaledPoint> compilePath(PathNode current) {
        ArrayList<ScaledPoint> path = new ArrayList<>();
        while (current.previous != null) {
            if (current.direction != current.previous.direction) {
                path.add(current.position);
            }
            current = current.previous;
        }
        return path;
    }
 */

    void discover(PathNode previous, Direction direction) {
        // Get the position to discover
        ScaledPoint position = previous.position;
        position.move(Scale.Backend, direction.getX(), direction.getY());

        if (visited.containsKey(position)) {
            return;
        }

        if (!boxGrid.isOccupied(position)) {
            if (position.equals(destination)) {
                return;
            }
        }

        // Generate the newly discovered node
        PathNode node = new PathNode(this, previous.relation);
        node.previous = previous;
        node.direction = direction;
        node.position = position;

        // Calculate the cost
        int cost = previous.cost + stepCost;

        // If direction changes add the cost of bending
        if (direction != previous.direction) cost += bendCost;

        // If the node is occupied and the lines shouldn't merge
        if (!relationGrid.canMergeLines(previous.relation, position)) cost += crossCost;

        // Add the node to unvisited
        node.cost = cost;
        discovered.add(node);
    }

    int getCostEstimate(PathNode node) {
        int costEstimate;
        costEstimate  = node.cost;
        costEstimate += manhattanDistance(node.position, destination);
        costEstimate += minBends(node.position, destination);

        return costEstimate;
    }

    private static int manhattanDistance(ScaledPoint from, ScaledPoint to) {
        return (Math.abs(from.getX(Scale.Backend) - to.getX(Scale.Backend)) + Math.abs(from.getY(Scale.Backend) - to.getY(Scale.Backend)));
    }

    private static int minBends(ScaledPoint position, ScaledPoint destination) {
        return 0; // Något något snabbare algoritm genom att inkludera
    }

}
