import java.util.List;

public class Route {
    private int routeId;
    private List<String> stations;

    public Route(int routeId, List<String> stations) {
        this.routeId = routeId;
        this.stations = stations;
    }

    // Getters and Setters
    public int getRouteId() { return routeId; }
    public List<String> getStations() { return stations; }

    public void setRouteId(int routeId) { this.routeId = routeId; }
    public void setStations(List<String> stations) { this.stations = stations; }

    @Override
    public String toString() {
        return "Route{" +
               "routeId=" + routeId +
               ", stations=" + stations +
               '}';
    }
}
