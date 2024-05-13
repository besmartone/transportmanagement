import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {
    private Map<String, List<Edge>> adjacencyList;

    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    /**
     * Adds an edge to the graph with transportation type.
     * @param src The starting station.
     * @param dest The destination station.
     * @param distance The distance between the stations.
     * @param transportType The type of transportation ("Bus" or "Train").
     */
    public void addEdge(String src, String dest, int distance, String transportType) {
        this.adjacencyList.putIfAbsent(src, new ArrayList<>());
        this.adjacencyList.putIfAbsent(dest, new ArrayList<>());
        this.adjacencyList.get(src).add(new Edge(dest, distance, transportType));
        this.adjacencyList.get(dest).add(new Edge(src, distance, transportType)); // If the graph is undirected
    }

      public Set<String> getAllVertices() {
        return adjacencyList.keySet();
    }
    /**
     * Gets the edges from a specific station.
     * @param node The station for which to get the edges.
     * @return A list of edges from the specified station.
     */
    public List<Edge> getEdges(String node) {
        return this.adjacencyList.getOrDefault(node, new ArrayList<>());
    }

    /**
     * Retrieves all nodes in the graph with their associated edges.
     * @return A map of all nodes and their edges.
     */
    public Map<String, List<Edge>> getAllNodes() {
        return this.adjacencyList;
    }
}
