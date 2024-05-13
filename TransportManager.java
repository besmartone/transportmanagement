import java.util.Collections;
import java.util.List;

public class TransportManager {
    public static void sortBusesByCapacity(List<Bus> buses) {
        Collections.sort(buses, (bus1, bus2) -> bus1.getCapacity() - bus2.getCapacity());
    }

    public static Route searchRouteByNumber(List<Route> routes, int routeId) {
        for (Route route : routes) {
            if (route.getRouteId() == routeId) {
                return route;
            }
        }
        return null; // No route found
    }
}
