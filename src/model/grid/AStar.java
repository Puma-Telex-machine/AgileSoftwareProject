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

    PriorityQueue<PathNode> discovered;
    TreeMap<ScaledPoint, PathNode> visited;

    AStar(IDiagram grid) {
        this.grid = grid;
    }

    ScaledPoint destination;

    public PathNode findPath(Relation relation) throws Exception {

        visited = new TreeMap<>();
        discovered = new PriorityQueue<>(Comparator.comparingInt(this::getCostEstimate));

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
        throw new Exception("No path found (There should always be a path)");
    }

    private void discover(PathNode previous, Direction direction) {
        // Get the position to discover
        ScaledPoint position = previous.position;
        position = position.move(Scale.Backend, direction.getX(), direction.getY());

        if (visited.containsKey(position)) {
            return;
        }

        if (grid.isOccupied(position)) {
            if (!position.equals(destination)) {
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
        if (!grid.canMergeLines(previous.relation, position)) cost += crossCost;

        // Add the node to unvisited
        node.cost = cost;
        discovered.add(node);
    }

    private int getCostEstimate(PathNode node) {
        return node.cost + manhattanDistance(node.position, destination) + minBends(node.position, destination);
    }

    private int minBends(ScaledPoint position, ScaledPoint destination) {
        if (position.getX(Scale.Backend) != destination.getX(Scale.Backend)) return bendCost;
        if (position.getY(Scale.Backend) != destination.getY(Scale.Backend)) return bendCost;
        return 0;
    }

    private static int manhattanDistance(ScaledPoint from, ScaledPoint to) {
        return (Math.abs(from.getX(Scale.Backend) - to.getX(Scale.Backend)) + Math.abs(from.getY(Scale.Backend) - to.getY(Scale.Backend)));
    }
}
