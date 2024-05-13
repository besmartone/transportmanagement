public class Edge {
    private String destination;
    private int distance;
    private String transportType;  // New field to store the type of transportation

    public Edge(String destination, int distance, String transportType) {
        this.destination = destination;
        this.distance = distance;
        this.transportType = transportType;
    }

    public String getDestination() { return destination; }
    public int getDistance() { return distance; }
    public String getTransportType() { return transportType; }  // Getter for the transport type
}
