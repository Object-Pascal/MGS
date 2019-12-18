package com.example.gaanplaatsstaan;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
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

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

import DataTier.Database.DatabaseManager;
import LogicTier.RouteManager.GpsManager.GPSManager;
import LogicTier.RouteManager.Route.OnRouteCallback;
import LogicTier.RouteManager.Route.Route;
import LogicTier.RouteManager.Route.RouteReader;
import LogicTier.RouteManager.Route.Waypoint;
import LogicTier.RouteManager.RouteManager;
import PresentationTier.Fragments.LegendaFragment;
import PresentationTier.Fragments.Setting.Settings;
import PresentationTier.Fragments.WaypointInfoFragment;
import PresentationTier.Fragments.WaypointPopup;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, OnRouteCallback {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private GoogleMap mMap;

    private GPSManager gpsManager;
    private Location lastLocation;
    private Marker currLocationMarker;

    private ArrayList<Polyline> routeLines;
    private RouteManager routeManager;

    private DatabaseManager databaseManager;
    private Settings settings;
    private RouteReader routeReader;
    private Route initialRoute;
    private boolean legendaVisible;
    private boolean mapSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        checkLocationPermission();
        initDefaultValues();
        loadLegend();
        readRouteFromDatabase();
        readSettingsFromDatabase();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initDefaultValues() {
        this.mapSwitch = false;
        this.routeLines = new ArrayList<>();
        this.legendaVisible = false;
        this.routeReader = new RouteReader(this);
    }

    private void readRouteFromDatabase() {
        ArrayList<Waypoint> wp = new ArrayList<>();
        wp.add(new Waypoint(false, false, "VVV", "51.588762", "4.776913", 0, null));
        wp.add(new Waypoint(false, false, "Grote Kerk Breda", "51.588770", "4.775376", 0, null));
        wp.add(new Waypoint(false, false, "Kippie Breda", "51.588271", "4.775229", 0, null));

        this.initialRoute = new Route(wp);
        //this.initialRoute = new Route(routeReader.ReadWaypointsFromDatabase());
    }

    private void readSettingsFromDatabase() {
        databaseManager = new DatabaseManager(this);
        this.settings = databaseManager.getSettingsFromDB();
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
            googleMap.addMarker(new MarkerOptions().position(waypointMarker).title(currWaypoint.getName()));
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

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            this.gpsManager.buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
}