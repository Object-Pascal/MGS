package com.example.gaanplaatsstaan;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import LogicTier.RouteManager.Route.Route;
import LogicTier.RouteManager.Route.RouteReader;
import LogicTier.RouteManager.Route.Waypoint;
import PresentationTier.Fragments.LegendaFragment;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private RouteReader routeReader;
    private Route initialRoute;
    private boolean legendaVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        initDefaultValues();
        readRouteFromDatabase();

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
        ImageView imageView = findViewById(R.id.imgLegenda);
        imageView.setOnClickListener(new View.OnClickListener() {
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
    }

    private void initDefaultValues() {
        this.legendaVisible = false;
        this.routeReader = new RouteReader(this);
    }

    private void readRouteFromDatabase() {
        this.initialRoute = new Route(routeReader.ReadWaypointsFromDatabase());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (Waypoint w : this.initialRoute.getRoute()) {
            LatLng waypointMarker = new LatLng(Double.parseDouble(w.getLatitude()), Double.parseDouble(w.getLongitude()));
            googleMap.addMarker(new MarkerOptions().position(waypointMarker).title(w.getName()));
        }
    }
}