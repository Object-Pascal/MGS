package LogicTier.RouteManager;

import java.util.ArrayList;

import LogicTier.RouteManager.Route.Waypoint;

public class RouteManager {
    private ArrayList<Waypoint> waypointsWithinDistance;

    public RouteManager() {
        this.waypointsWithinDistance = new ArrayList<>();
    }

    public boolean CheckIfCloseToWaypoint(int x, int y) {
        // TODO: Check maken
        return true;
    }

    public ArrayList<Waypoint> getWaypointsWithinDistance() {
        return waypointsWithinDistance;
    }

    public void setWaypointsWithinDistance(ArrayList<Waypoint> waypointsWithinDistance) {
        this.waypointsWithinDistance = waypointsWithinDistance;
    }
}