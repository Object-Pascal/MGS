package LogicTier.RouteManager.Route;

import java.util.ArrayList;

public class Route {
    private ArrayList<Waypoint> route;

    public Route() {
        this.route = new ArrayList<>();
    }

    public ArrayList<Waypoint> getRoute() {
        return route;
    }

    public void setRoute(ArrayList<Waypoint> route) {
        this.route = route;
    }
}