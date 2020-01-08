package LogicTier.RouteManager.Route;

import android.content.Context;
import java.util.ArrayList;

import DataTier.Database.DatabaseManager;

public class RouteReader {
    private RouteHelper routeHelper;

    public RouteReader(Context context) {
        this.routeHelper = new RouteHelper();
    }

    public Route ReadRouteFromJson(Context c, String routeName) {
        return this.routeHelper.getRoute(c, routeName);
    }
}