package model.grid;

import model.relations.Relation;

import java.awt.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class RelationGrid {

    Grid<GridNode> relationGrid;
    BoxGrid boxGrid;

    TreeMap<Point, RelationNode> visited = new TreeMap<>((p1, p2) -> {
        if (p1.x < p2.x) return -1;
        else if (p1.x > p2.x) return 1;
        else {
            return Integer.compare(p1.y, p2.y);
        }
    });
    PriorityQueue<RelationNode> discovered = new PriorityQueue<>();

    int stepCost = 1;
    int bendCost = 10;
    int crossCost = 100;

    public RelationGrid(BoxGrid boxGrid, int width, int height) {
        this.boxGrid = boxGrid;
        this.relationGrid = new Grid<>();
    }

    public ArrayList<Point> addRelation(Relation relation) {
        RelationNode startNode = new RelationNode(relation);
        startNode.position = new Point(relation.getFrom().getPosition());
        //startNode.position.translate(5, -10);
        startNode.cost = 0;
        discovered.add(startNode);

        while (!discovered.isEmpty()) {
            RelationNode current = discovered.remove();

            Point currentPoint = current.position;

            if (current.position.equals(current.destination)) return compilePath(current);

            visited.put(current.position, current);

            discover(current, Direction.UP);
            discover(current, Direction.DOWN);
            discover(current, Direction.LEFT);
            discover(current, Direction.RIGHT);
        }
        return null;
    }

    private ArrayList<Point> compilePath(RelationNode current) {
        ArrayList<Point> path = new ArrayList<>();
        while (current.previous != null) {
            path.add(current.position);
            current = current.previous;
        }
        return path;
    }

    void discover(RelationNode previous, Direction direction) {
        // Get the position to discover
        Point position = new Point(previous.position);
        position.translate(direction.x, direction.y);

        if (visited.containsKey(position)) {
            return;
        }

        if (!boxGrid.isEmpty(position)) {
            if (!position.equals(previous.destination)) {
                return;
            }
        }

        // Generate the newly discovered node
        RelationNode node = new RelationNode(previous.relation);
        node.previous = previous;
        node.direction = direction;
        node.position = position;

        // Calculate the cost
        int cost = previous.cost + stepCost;

        // If direction changes add the cost of bending
        if (direction != previous.direction) cost += bendCost;

        // If the node is occupied and the lines shouldn't merge
        //if (!relationGrid.get(position).canMergeLines(node)) cost += crossCost;

        // Add the node to unvisited
        node.cost = cost;
        discovered.add(node);
    }

    private static int getCostEstimate(RelationNode node) {
        return manhattanDistance(node.position, node.destination) + node.cost;
    }

    private static int manhattanDistance(Point from, Point to) {
        return (int) (Math.abs(from.getX() - to.getX()) + Math.abs(from.getY() - to.getY()));
    }

    private static class RelationNode implements Comparable<RelationNode> {
        Relation relation;
        Point destination;

        RelationNode previous;
        Direction direction;
        Point position;
        int cost;

        public RelationNode(Relation relation) {
            this.relation = relation;
            this.destination = relation.getTo().getPosition(); //Dumt?
        }

        @Override
        public int compareTo(RelationNode o) {
            return getCostEstimate(this) - getCostEstimate(o); // Correct order?
        }
    }

    private static class GridNode {
        ArrayList<RelationNode> relationNodes = new ArrayList<>(); //Better data structure?

        boolean canMergeLines(RelationNode relationNode) {
            for (RelationNode n : relationNodes) {
                if (n.relation.getTo() == relationNode.relation.getTo() && n.relation.getArrowType() == relationNode.relation.getArrowType())
                    return true;
                }
            return false;
        }

        void addRelation(RelationNode relationNode) {
            relationNodes.add(relationNode);
        }

        void removeRelation(RelationNode relationNode) {
            relationNodes.remove(relationNode);
        }
    }

    private enum Direction {
        UP(0, -1),
        DOWN(0, 1),
        LEFT(-1, 0),
        RIGHT(1, 0);

        private final int x;
        private final int y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
