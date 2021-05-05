package model.grid;

import model.point.Scale;
import model.point.ScaledPoint;
import model.relations.Relation;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class AStar {

    IDiagram grid;

    int stepCost = 1;
    int bendCost = 10;
    int crossCost = 100;

    PriorityQueue<PathNode> discovered = new PriorityQueue<>(Comparator.comparingInt(this::getCostEstimate));
    TreeMap<ScaledPoint, PathNode> visited = new TreeMap<>();

    public AStar(IDiagram grid) {
    }

    ScaledPoint destination;

    public PathNode findPath(Relation relation) {
        destination = relation.getToPosition();

        PathNode startNode = new PathNode(relation);
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
        PathNode node = new PathNode(previous.relation);
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
        return node.cost + manhattanDistance(node.position, destination);
    }

    private static int manhattanDistance(ScaledPoint from, ScaledPoint to) {
        return (Math.abs(from.getX(Scale.Backend) - to.getX(Scale.Backend)) + Math.abs(from.getY(Scale.Backend) - to.getY(Scale.Backend)));
    }
}
