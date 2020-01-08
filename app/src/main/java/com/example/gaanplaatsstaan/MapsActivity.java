package com.example.gaanplaatsstaan;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import DataTier.Database.DatabaseManager;
import LogicTier.RouteManager.GpsManager.GPSManager;
import LogicTier.RouteManager.Route.OnRouteCallback;
import LogicTier.RouteManager.Route.Route;
import LogicTier.RouteManager.Route.RouteReader;
import LogicTier.RouteManager.Route.Waypoint;
import LogicTier.RouteManager.RouteManager;
import PresentationTier.Fragments.LegendaFragment;
import PresentationTier.Fragments.RoutesFragment;
import PresentationTier.Fragments.Setting.Settings;
import PresentationTier.Fragments.WaypointInfoFragment;
import PresentationTier.Fragments.WaypointPopup;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, OnRouteCallback, GoogleMap.OnMarkerClickListener {
    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;

    private GPSManager gpsManager;
    private Location lastLocation;
    private Marker currLocationMarker;

    private ArrayList<Polyline> routeLines;
    private RouteManager routeManager;
    private boolean isPaused = false;
    private DatabaseManager databaseManager;
    private Settings settings;
    private RouteReader routeReader;
    private Route initialRoute;
    private Route exampleRoute2;
    private boolean legendaVisible;
    private boolean routesVisable;
    private boolean mapSwitch;
    private SupportMapFragment mapFragment;

    private ArrayList<Route> routes = new ArrayList<>();
    private ArrayAdapter<Route> adapter;
    private ArrayAdapter<Waypoint> adapterForWaypoints;
    private ArrayList<Waypoint> waypointsToDisplay = new ArrayList<>();
    private ListView routesListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        initDefaultValues();
        loadLegend();
        loadRoutes();
        readRouteFromDatabase();
        readSettingsFromDatabase();

        routes.add(initialRoute);
        routes.add(exampleRoute2);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //setErrorImg(true);
    }

    private void initDefaultValues() {
        this.mapSwitch = false;
        this.routeLines = new ArrayList<>();
        this.legendaVisible = false;
        this.routeReader = new RouteReader(this);
    }

    private void readRouteFromDatabase() {
        // De database ondervindt problemen dus de waypoints worden hardcoded toegevoegd
        // this.initialRoute = new Route(routeReader.ReadWaypointsFromDatabase());

        ArrayList<Waypoint> wp = new ArrayList<Waypoint>();
        wp.add(new Waypoint(false, false, "VVV", "", "51.588762", "4.776913", 0, null));
        wp.add(new Waypoint(false, false, "De Bruine Pij", "", "51.588791", "4.775477", 0, null));
        wp.add(new Waypoint(false, false, "Corenmaet", "", "51.589308", "4.774484", 0, null));
        wp.add(new Waypoint(false, false, "O'Mearas Irish Pub", "", "51.589448", "4.775846", 0, null));
        wp.add(new Waypoint(false, false, "Kasteel van Breda", "", "51.590504", "4.776221", 0, null));
        wp.add(new Waypoint(false, false, "Stadspark Valkenberg", "", "51.590613", "4.777537", 0, null));
        wp.add(new Waypoint(false, false, "Café Publieke Werken", "", "51.588973", "4.778062", 0, null));
        this.initialRoute = new Route(wp);


        //ROUTE 2
        /*DatabaseManager databaseManager = new DatabaseManager(this);
        databaseManager.insertWaypointIntoDB(new Waypoint(false, false, "VVV", "51.588762", "4.776913", 0, null));
        databaseManager.insertWaypointIntoDB(new Waypoint(false, false, "Grote Kerk Breda", "51.588770", "4.775376", 0, null));
        databaseManager.insertWaypointIntoDB(new Waypoint(false, false, "Kippie Breda", "51.588271", "4.775229", 0, null));

        Waypoint vvv = new Waypoint(false, false, "VVV", "51.588762", "4.776913", 0, null);
        Waypoint avans = new Waypoint(false, false, "Avans Lovensdijkstraat", "51.585665", "4.792003",0, null);
        Waypoint universityOfAppliedSciences = new Waypoint(false, false, "B U O A S", "51.590784", "4.795631", 0 , null);
        Waypoint hogeSchoollaan = new Waypoint(false, false, "Avans Hogeschoollaan", "51.584095", "4.797019", 0, null);

        ArrayList<Waypoint> waypoints = new ArrayList<>();
        waypoints.add(vvv);
        waypoints.add(avans);
        waypoints.add(universityOfAppliedSciences);
        waypoints.add(hogeSchoollaan);

        exampleRoute2 = new Route(waypoints);
        exampleRoute2.title = "Scholen tour";
        exampleRoute2.image = getDrawable(R.drawable.avans);

        this.initialRoute = new Route(routeReader.ReadWaypointsFromDatabase());
        this.initialRoute.title = "VVV route";
        this.initialRoute.image = getDrawable(R.drawable.bredastad);*/
    }

    private void readSettingsFromDatabase() {
        databaseManager = new DatabaseManager(this);
        this.settings = databaseManager.getSettingsFromDB();
    }

    private void loadRoutes() {
        //Routes inladen
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        RoutesFragment routesFragment = new RoutesFragment();
        ft.replace(R.id.routeFrame, routesFragment);
        ft.commit();

        //Onzichtbaar maken
        FrameLayout frameLayout = findViewById(R.id.routeFrame);
        frameLayout.setVisibility(View.INVISIBLE);

        //Onclick
        ImageView routeButton = (ImageView) findViewById(R.id.imgRoutes);
        routeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final FrameLayout frameLayout = findViewById(R.id.routeFrame);
                frameLayout.setVisibility(routesVisable ? View.INVISIBLE : View.VISIBLE);
                routesVisable = !routesVisable;
                final ImageView bottomButton = (ImageView) findViewById(R.id.imgRoutes);
                bottomButton.setVisibility(View.INVISIBLE);
                ImageView topButton = (ImageView) findViewById(R.id.img_pullup);
                topButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        frameLayout.setVisibility(routesVisable ? View.INVISIBLE : View.VISIBLE);
                        routesVisable = !routesVisable;
                        bottomButton.setVisibility(View.VISIBLE);
                    }
                });
                routeMenu();
                loadRouteListView();

            }
        });
    }

    private void routeMenu() {
        Log.i(TAG, "routeMenu: Clicked");
        final ImageView playPause = (ImageView)(findViewById(R.id.img_pauseplay));
        playPause.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                playPause.setImageResource(isPaused ? android.R.drawable.ic_media_pause : android.R.drawable.ic_media_play);
                isPaused=!isPaused;
            }
        });
        final ImageView stop = (ImageView)findViewById(R.id.img_stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Gestopt met app", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        final ImageView restart = (ImageView)findViewById(R.id.img_again);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Je route begint nu opnieuw", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadRouteListView() {
        routesListView = (ListView) findViewById(R.id.routes_list);
        adapter = new ArrayAdapter<Route>(getApplicationContext(), R.layout.part_route, routes) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null)
                    convertView = getLayoutInflater().inflate(R.layout.part_route, null);
                ImageView appIcon = (ImageView) convertView.findViewById(R.id.part_route_iv_icon);
                appIcon.setImageDrawable(routes.get(position).image);
                ImageView background = (ImageView) convertView.findViewById(R.id.part_route_iv_background);
                int number = (int)(Math.random()*3);
                background.setImageDrawable(getDrawable(number==0 ? android.R.color.holo_orange_light : number==1 ?  android.R.color.holo_green_dark : number==2 ? android.R.color.holo_red_dark : number==3 ? android.R.color.holo_purple : android.R.color.holo_green_light));
                TextView title = (TextView) convertView.findViewById(R.id.part_route_tv_title);
                title.setText(routes.get(position).title);
                return convertView;
            }
        };
        routesListView.setAdapter(adapter);
        addClickListenerForRoutes();
    }

    private void loadWaypointListView(final ArrayList<Waypoint> waypoints) {
        routesListView = (ListView) findViewById(R.id.routes_list);
        adapterForWaypoints = new ArrayAdapter<Waypoint>(getApplicationContext(), R.layout.part_waypoint, waypoints) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView==null)convertView= getLayoutInflater().inflate(R.layout.part_waypoint, null);
                ImageView appIcon = (ImageView) convertView.findViewById(R.id.part_waypoint_iv_icon4);
                int number = (int)(Math.random()*3);
                appIcon.setImageDrawable(getDrawable(number==0 ? R.drawable.bredastad : number==1 ?  android.R.color.holo_green_dark : number==2 ? R.drawable.avans : number==3 ? android.R.color.holo_purple : R.drawable.circle_good));
                ImageView background = (ImageView) convertView.findViewById(R.id.part_waypoint_iv_background4);
                number = (int)(Math.random()*3);
                background.setImageDrawable(getDrawable(number==0 ? android.R.color.holo_orange_light : number==1 ?  android.R.color.holo_green_dark : number==2 ? android.R.color.holo_red_dark : number==3 ? android.R.color.holo_purple : android.R.color.holo_green_light));
                TextView title = (TextView) convertView.findViewById(R.id.part_waypoint_tv_title4);
                title.setText(waypoints.get(position).getName());
                return convertView;
            }
        };
        routesListView.setAdapter(adapterForWaypoints);
    }

    private void addClickListenerForRoutes() {
        routesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // fill with waypoints instead of routes.
                loadWaypointListView(exampleRoute2.getRoute());
            }
        });
    }

    private void loadLegend() {
        //Legenda inladen
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.legendFrame, new LegendaFragment());
        ft.commit();

        //Onzichtbaar maken
        FrameLayout frameLayout = findViewById(R.id.legendFrame);
        frameLayout.setVisibility(View.INVISIBLE);

        //Onclick aanmaken.
        ImageView imgLegend = findViewById(R.id.imgLegenda);
        imgLegend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameLayout frameLayout = findViewById(R.id.legendFrame);
                if(legendaVisible) {
                    frameLayout.setVisibility(View.INVISIBLE);
                }
                else {
                    frameLayout.setVisibility(View.VISIBLE);
                }
                legendaVisible = !legendaVisible;
            }
        });

        ImageView imgMap = findViewById(R.id.imgMap);
        imgMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mapSwitch) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
                else {
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }
                mapSwitch = !mapSwitch;
            }
        });


        ImageView imgSettings = findViewById(R.id.imgSettings);
        imgSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        googleMap.setOnMarkerClickListener(this);

        if(!this.settings.isColorBlindmode()) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        else {
            googleMap.setMapStyle(new MapStyleOptions(getResources().getString(R.string.style_json)));
        }

        initializeUserConnection();
        this.routeManager = new RouteManager(this, this.initialRoute, this);

        for (int i = 0; i < this.initialRoute.getRoute().size(); i++) {
            Waypoint currWaypoint = this.initialRoute.getRoute().get(i);

            LatLng waypointMarker = new LatLng(Double.parseDouble(currWaypoint.getLatitude()), Double.parseDouble(currWaypoint.getLongitude()));

            googleMap.addMarker(new MarkerOptions().position(waypointMarker).title(currWaypoint.getName())).setTag(currWaypoint);
            googleMap.addCircle(new CircleOptions().center(waypointMarker).radius(10).strokeColor(Color.RED).strokeWidth(2.0f));

            if (i + 1 != this.initialRoute.getRoute().size()) {
                Waypoint nextWaypoint = this.initialRoute.getRoute().get(i + 1);

                this.routeManager.fetchRoute(
                    new LatLng(Double.parseDouble(currWaypoint.getLatitude()), Double.parseDouble(currWaypoint.getLongitude())),
                    new LatLng(Double.parseDouble(nextWaypoint.getLatitude()), Double.parseDouble(nextWaypoint.getLongitude()))
                );
            }
            else {
                this.routeManager.fetchRoute(
                    new LatLng(Double.parseDouble(currWaypoint.getLatitude()), Double.parseDouble(currWaypoint.getLongitude())),
                    new LatLng(Double.parseDouble(this.initialRoute.getRoute().get(0).getLatitude()), Double.parseDouble(this.initialRoute.getRoute().get(0).getLongitude()))
                );
            }
        }

        this.routeManager = new RouteManager(this, this.initialRoute, this);
    }

    @Override
    public void OnRouteLoaded(PolylineOptions options, LatLngBounds bounds, int padding) {
        routeLines.add(mMap.addPolyline(options));
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
    }

    public void setErrorImg(boolean hasError) {
        ImageView errorImg = (ImageView) findViewById(R.id.imgWorking);
        errorImg.setImageResource(hasError ? R.drawable.circle_bad : R.drawable.circle_good);
    }

    private void initializeUserConnection() {
        this.gpsManager = new GPSManager(new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                    lastLocation = location;
                    if (currLocationMarker != null) {
                        currLocationMarker.remove();
                    }

                    Waypoint closestWaypoint = routeManager.CheckIfCloseToWaypoint(lastLocation.getLatitude(), lastLocation.getLongitude());
                    if (closestWaypoint != null) {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        WaypointPopup waypointPopup = new WaypointPopup();
                        ft.replace(R.id.legendFrame, waypointPopup);
                        ft.commit();
                        waypointPopup.ShowPopUpFragment(closestWaypoint.getName());
                    }
                }
            }
        }, this);

        this.gpsManager.checkLocationPermission(this);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            this.gpsManager.buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Waypoint waypoint = (Waypoint)marker.getTag();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new WaypointInfoFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("waypoint", waypoint);

        fragment.setArguments(bundle);
        ft.replace(R.id.map, fragment);
        ft.commit();
        return true;
    }
}