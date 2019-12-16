package PresentationTier.MainActivity;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gaanplaatsstaan.R;

import PresentationTier.Fragments.ErrorFragment;

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