import java.util.*;

public class DijkstraAlgorithm {

    public static List<String> findShortestPath(Graph graph, String start, String end) {
        // Map to store the shortest distance from start to each node
        Map<String, Integer> distances = new HashMap<>();
        // Map to track the optimal paths
        Map<String, String> previous = new HashMap<>();
        // Priority queue to select the node with the smallest distance
        PriorityQueue<String> nodes = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        // Initialize distances and queue
        for (String vertex : graph.getAllVertices()) {
            if (vertex.equals(start)) {
                distances.put(vertex, 0);
            } else {
                distances.put(vertex, Integer.MAX_VALUE);
            }
            nodes.add(vertex);
        }

        while (!nodes.isEmpty()) {
            String smallest = nodes.poll();
            if (smallest.equals(end)) {
                return buildPath(previous, end);
            }

            if (distances.get(smallest) == Integer.MAX_VALUE) {
                break;
            }

            // Process each adjacent node
            for (Edge edge : graph.getEdges(smallest)) {
                String adjacent = edge.getDestination();
                int newDist = distances.get(smallest) + edge.getDistance();
                if (newDist < distances.get(adjacent)) {
                    distances.put(adjacent, newDist);
                    previous.put(adjacent, smallest);
                    nodes.add(adjacent);  // Re-evaluate this node in the queue with the new distance
                }
            }
        }

        return new ArrayList<>();  // Return an empty list if no path is found
    }
    
    private static List<String> buildPath(Map<String, String> previous, String end) {
        List<String> path = new ArrayList<>();
        for (String at = end; at != null; at = previous.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }
}
