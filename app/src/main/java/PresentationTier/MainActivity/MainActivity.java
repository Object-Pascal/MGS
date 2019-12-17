package PresentationTier.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
;
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

        //databaseManager.insertWaypointIntoDB(new Waypoint(false, false, 1, "VVV", "51.588762", "4.776913", 0, null));
        //databaseManager.insertWaypointIntoDB(new Waypoint(false, false, 2, "Grote Kerk Breda", "51.588770", "4.775376", 0, null));

        Intent mapsIntent = new Intent(this, MapsActivity.class);
        startActivity(mapsIntent);
    }
}