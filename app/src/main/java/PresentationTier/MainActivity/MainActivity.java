package PresentationTier.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.gaanplaatsstaan.MapsActivity;
import com.example.gaanplaatsstaan.R;

import DataTier.Database.DatabaseManager;
import LogicTier.RouteManager.Route.Waypoint;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseManager databaseManager = new DatabaseManager(this);

        databaseManager.insertWaypointIntoDB(new Waypoint(false, false, "VVV", "51.588762", "4.776913", 0, null));
        databaseManager.insertWaypointIntoDB(new Waypoint(false, false, "De Bruine Pij", "51.588791", "4.775477", 0, null));
        databaseManager.insertWaypointIntoDB(new Waypoint(false, false, "Corenmaet", "51.589308", "4.774484", 0, null));
        databaseManager.insertWaypointIntoDB(new Waypoint(false, false, "O'Mearas Irish Pub", "51.589448", "4.775846", 0, null));
        databaseManager.insertWaypointIntoDB(new Waypoint(false, false, "Kasteel van Breda", "51.590504", "4.776221", 0, null));
        databaseManager.insertWaypointIntoDB(new Waypoint(false, false, "Café Publieke Werken", "51.588973", "4.778062", 0, null));

        Intent mapsIntent = new Intent(this, MapsActivity.class);
        startActivity(mapsIntent);
    }
}