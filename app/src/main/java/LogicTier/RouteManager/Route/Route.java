package LogicTier.RouteManager.Route;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class Route {
    private ArrayList<Waypoint> route;

    public String title;
    public Drawable image;

    public Route() {
        this.route = new ArrayList<>();
    }

    public Route(ArrayList<Waypoint> route){
        this.route = route;
    }

    public ArrayList<Waypoint> getRoute() {
        return route;
    }

    public void setRoute(ArrayList<Waypoint> route) {
        this.route = route;
    }
}