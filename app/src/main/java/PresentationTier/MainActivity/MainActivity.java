package PresentationTier.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import com.example.gaanplaatsstaan.MapsActivity;
import com.example.gaanplaatsstaan.R;

import java.util.ArrayList;

import DataTier.Database.DatabaseManager;
import LogicTier.RouteManager.Route.Route;
import LogicTier.RouteManager.Route.RouteHelper;
import LogicTier.RouteManager.Route.Waypoint;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*        DatabaseManager databaseManager = new DatabaseManager(this);

        databaseManager.insertWaypointIntoDB(new Waypoint(false, false, "VVV", "51.588762", "4.776913", 0, null));
        databaseManager.insertWaypointIntoDB(new Waypoint(false, false, "De Bruine Pij", "51.588791", "4.775477", 0, null));
        databaseManager.insertWaypointIntoDB(new Waypoint(false, false, "Corenmaet", "51.589308", "4.774484", 0, null));
        databaseManager.insertWaypointIntoDB(new Waypoint(false, false, "OMearas Irish Pub", "51.589448", "4.775846", 0, null));
        databaseManager.insertWaypointIntoDB(new Waypoint(false, false, "Kasteel van Breda", "51.590504", "4.776221", 0, null));
        databaseManager.insertWaypointIntoDB(new Waypoint(false, false, "Café Publieke Werken", "51.588973", "4.778062", 0, null));*/

        ArrayList<Waypoint> wp = new ArrayList<Waypoint>();
        ArrayList<String> images = new ArrayList<>();
        images.add("https://vvvbreda.nl/content/uploads/2019/03/IMG_20190328_121002.jpg");
        wp.add(new Waypoint(false, false, "VVV", "Dit fraaie stadspark verbindt het station van Breda met de binnenstad." +
                "" +
                "" +
                "Tot 1812 deed het Valkenberg dienst als kasteeltuin voor de Heren van Breda. Het dankt zijn naam aan een valkenhuis dat aan de rand stond en van waaruit de kasteelbewoners en hun gasten de valkenjacht bedreven.", "51.588762", "4.776913", 0, images));
        images = new ArrayList<>();
        images.add("https://media-cdn.tripadvisor.com/media/photo-s/15/a4/74/91/buiten-kant.jpg");
        wp.add(new Waypoint(false, false, "De Bruine Pij", "a", "51.588791", "4.775477", 0, images));
        images = new ArrayList<>();
        images.add("https://corenmaet.nl/wp-content/media/buiten.jpg");
        wp.add(new Waypoint(false, false, "Corenmaet", "a", "51.589308", "4.774484", 0, images));
        images = new ArrayList<>();
        images.add("https://indebuurt.nl/breda/wp-content/uploads/2018/03/omaeras-e1520954240271.jpg");
        wp.add(new Waypoint(false, false, "O'Mearas Irish Pub", "a", "51.589448", "4.775846", 0, images));
        images = new ArrayList<>();
        images.add("http://johnooms.nl/wp-content/uploads/2017/06/Kasteel-van-Breda1.jpg");
        wp.add(new Waypoint(false, false, "Kasteel van Breda", "a", "51.590504", "4.776221", 0, images));
        images = new ArrayList<>();
        images.add("https://vvvbreda.nl/content/uploads/2018/08/Valkenbergpark-Breda-1-origineel-foto-Mari%C3%ABlle-Houben-1-e1563801409597-1120x604.jpg");
        wp.add(new Waypoint(false, false, "Stadspark Valkenberg", "Dit fraaie stadspark verbindt het station van Breda met de binnenstad.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"Tot 1812 deed het Valkenberg dienst als kasteeltuin voor de Heren van Breda. Het dankt zijn naam aan een valkenhuis dat aan de rand stond en van waaruit de kasteelbewoners en hun gasten de valkenjacht bedreven.", "51.590613", "4.777537", 0, images));
        images = new ArrayList<>();
        images.add("https://indebuurt.nl/breda/wp-content/uploads/2017/08/publieke-werken-st-annastraat-breda-2.jpg");
        wp.add(new Waypoint(false, false, "Café Publieke Werken", "d", "51.588973", "4.778062", 0, images));


        RouteHelper routeHelper = new RouteHelper();
        routeHelper.uploadRoute(this, wp, "Route1");
        routeHelper.uploadRoute(this, wp, "Route3");

        Route route = routeHelper.getRoute(this, "Route1");
        ArrayList<String> routeNames = routeHelper.getAllRouteNames(this);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mapsIntent = new Intent(MainActivity.this, MapsActivity.class);
                    startActivity(mapsIntent);
                    finish();
                }
            },SPLASH_TIME_OUT );

        }
    }