package LogicTier.RouteManager;

import android.location.Location;

import LogicTier.RouteManager.Route.Route;
import LogicTier.RouteManager.Route.Waypoint;

public class RouteManager {
    private Route route;

    public RouteManager(Route route) {
        this.route = route;
    }

    public Waypoint CheckIfCloseToWaypoint(double userLatitude, double userLongitude) {
        Waypoint closestWaypoint = null;
        for (Waypoint w : this.route.getRoute()) {
            float[] checkResult = new float[1];
            Location.distanceBetween(Double.parseDouble(w.getLatitude()), Double.parseDouble(w.getLongitude()), userLatitude, userLongitude, checkResult);

            if (checkResult[0] < 10) {
                closestWaypoint = w;
            }
        }
        return closestWaypoint;
    }

    public Route getRoute() {
        return route;
    }
}