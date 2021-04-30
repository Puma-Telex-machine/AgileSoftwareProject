package model.pathfinder;

import java.awt.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class AStar {

    GridView gridView;

    PriorityQueue<Node> openSet = new PriorityQueue<>();
    TreeMap<Point, Integer> closedSet = new TreeMap<>((p1, p2) -> {
        if (p1.x < p2.x) return -1;
        else if (p1.x > p2.x) return 1;
        else {
            return Integer.compare(p1.y, p2.y);
        }
    });

    public AStar(GridView gridView) {
        this.gridView = gridView;
    }

    ArrayList<Point> findPath(Point start, Point goal) throws Exception {
        Node startNode = new Node();
        startNode.position = start;
        startNode.cost = 0;
        startNode.estimate = gridView.getEstimatedCost(start, goal);
        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.remove();
            if (current.position == goal) return reconstructPath(current);

            discover(current, goal, Direction.UP);
            discover(current, goal, Direction.DOWN);
            discover(current, goal, Direction.LEFT);
            discover(current, goal, Direction.RIGHT);
        }
        throw new Exception("No path found from " + startNode + " to " + goal);
    }

    void discover(Node current, Point goal, Direction direction) {
        Point point = new Point(current.position.x - direction.x, current.position.y - direction.y);

        if (!gridView.isOccupied(point)) {
            Node node = new Node();
            node.cost = node.cameFrom.cost + gridView.getMoveCost(node.position);

            if (!closedSet.containsKey(point) || closedSet.get(point) > node.cost) {
                node.cameFrom = current;
                node.position = point;
                node.estimate = gridView.getEstimatedCost(node.position, goal);

                openSet.add(node);
                closedSet.put(point, node.cost);
            }
        }
    }

    private ArrayList<Point> reconstructPath(Node goal) {
        ArrayList<Point> path = new ArrayList<>();
        Node current = goal;
        while (current.cameFrom != null) {
            path.add(current.position);
            current = current.cameFrom;
        }
        return path;
    }

    private static class Node implements Comparable<Node> {
        Node cameFrom;
        Point position;
        int cost;
        int estimate;

        @Override
        public int compareTo(Node o) {
            return Integer.compare(cost + estimate, o.cost);
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
