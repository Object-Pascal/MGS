package com.example.gaanplaatsstaan;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import DataTier.Database.DatabaseManager;
import LogicTier.RouteManager.Route.Route;
import LogicTier.RouteManager.Route.RouteReader;
import LogicTier.RouteManager.Route.Waypoint;
import PresentationTier.Fragments.LegendaFragment;
import PresentationTier.Fragments.Setting.Settings;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

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

        initDefaultValues();
        readRouteFromDatabase();

        databaseManager = new DatabaseManager(this);
        this.settings = databaseManager.getSettingsFromDB();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Legende inladen
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

    private void initDefaultValues() {
        this.mapSwitch = false;
        this.legendaVisible = false;
        this.routeReader = new RouteReader(this);
    }

    private void readRouteFromDatabase() {
        this.initialRoute = new Route(routeReader.ReadWaypointsFromDatabase());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if(!this.settings.isColorBlindmode()) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        else {
            googleMap.setMapStyle(new MapStyleOptions(getResources().getString(R.string.style_json)));

        }


        for (Waypoint w : this.initialRoute.getRoute()) {
            LatLng waypointMarker = new LatLng(Double.parseDouble(w.getLatitude()), Double.parseDouble(w.getLongitude()));
            googleMap.addMarker(new MarkerOptions().position(waypointMarker).title(w.getName()));
        }
    }
}