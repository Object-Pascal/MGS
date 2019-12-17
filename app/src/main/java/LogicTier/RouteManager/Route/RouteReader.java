package LogicTier.RouteManager.Route;

import android.content.Context;
import java.util.ArrayList;

import DataTier.Database.DatabaseManager;

public class RouteReader {
    private DatabaseManager databaseManager;

    public RouteReader(Context context) {
        this.databaseManager = new DatabaseManager(context);
    }

    public ArrayList<Waypoint> ReadWaypointsFromDatabase() {
        return this.databaseManager.getWaypointsFromDB();
    }
}