package PresentationTier.MainActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.gaanplaatsstaan.R;

import java.util.ArrayList;

import DataTier.Database.DatabaseManager;
import LogicTier.RouteManager.Route.Waypoint;
import PresentationTier.Fragments.ErrorFragment;
import PresentationTier.Fragments.Setting.Settings;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*Waypoint waypoint = new Waypoint(true, false, 1, "Test",
                "51.571915", "4.768323", 12, null);

        DatabaseManager databaseManager = new DatabaseManager(this);
        databaseManager.insertWaypointIntoDB(waypoint);

        ArrayList<Waypoint> test = databaseManager.getWaypointsFromDB();

        databaseManager.insertSettingsIntoDB(new Settings("NL"));
        Settings settings = databaseManager.getSettingsFromDB();*/


        Fragment f = new ErrorFragment();
        replaceFragment(f);

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameContainer, fragment);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
}