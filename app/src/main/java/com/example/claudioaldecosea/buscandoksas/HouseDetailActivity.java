package com.example.claudioaldecosea.buscandoksas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.claudioaldecosea.buscandoksas.domain.Habitaciones;
import com.example.claudioaldecosea.buscandoksas.domain.House;
import com.example.claudioaldecosea.buscandoksas.model.adapter.HouseDetailAdapter;
import com.example.claudioaldecosea.buscandoksas.model.adapter.RecycleViewClickListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HouseDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static String EXTRA_DATA = "extra_data";
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private House mData;
    private HouseDetailAdapter mHouseDetailAdapter;
    private RecyclerView mRecycleDetailView;
    private TextView barrio;
    private TextView precio;
    private TextView cuartos;
    private TextView metraje;
    private TextView banios;
    private TextView garage;
    private TextView parrillero;
    private TextView balcon;
    private TextView patio;

    private MapView mapView;
    private GoogleMap gmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_detail);

        Intent intent = getIntent();
        mData = intent.getParcelableExtra(EXTRA_DATA);


        barrio = findViewById(R.id.txt_house_detail_barrio);
        precio = findViewById(R.id.txt_house_detail_precio);
        cuartos = findViewById(R.id.txt_house_detail_cant_dormitorios);
        metraje = findViewById(R.id.txt_house_detail_metraje);

        banios = findViewById(R.id.txt_house_detail_banos);
        garage = findViewById(R.id.txt_house_detail_garage);
        parrillero = findViewById(R.id.txt_house_detail_parrillero);
        balcon = findViewById(R.id.txt_house_detail_balcon);
        patio = findViewById(R.id.txt_house_detail_patio);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }


        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        barrio.setText(mData.getInmuebleBarrio());
        precio.setText("$U " + mData.getInmueblePrecio());
        cuartos.setText(mData.getInmuebleCantDormitorio());
        metraje.setText(mData.getInmuebleMetrosCuadrados());
        banios.setText(String.valueOf(getNumeroBanos(mData)));
        garage.setText(Boolean.valueOf(mData.getInmuebleTieneGarage()) ? "Si" : "No");
        parrillero.setText(Boolean.valueOf(mData.getInmuebleTieneParrillero()) ? "Si" : "No");
        balcon.setText(Boolean.valueOf(mData.getInmuebleTieneBalcon()) ? "Si" : "No");
        patio.setText(Boolean.valueOf(mData.getInmuebleTienePatio()) ? "Si" : "No");


        RecycleViewClickListener recycleViewListener = new RecycleViewClickListener() {
            @Override
            public void onClick(View v, int pos) {

            }
        };

        mRecycleDetailView = findViewById(R.id.images_list);
        mHouseDetailAdapter = new HouseDetailAdapter(this, mData.getFotos(), recycleViewListener);
        mRecycleDetailView.setAdapter(mHouseDetailAdapter);

        mRecycleDetailView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    protected int getNumeroBanos(House mData) {

        int cantBanos = 0;
        for (Habitaciones h : mData.getHabitaciones()) {
            if (h.getInmuebleHabitacionNombre().equalsIgnoreCase("baños") || h.getInmuebleHabitacionNombre().equalsIgnoreCase("baño")) {
                return Integer.valueOf(h.getInmuebleHabitacionCantidad());
            }
        }

        return cantBanos;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34.8907837, -56.1142534);
        gmap.addMarker(new MarkerOptions().position(sydney).title("Casa aqui!"));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(-34.8907837, -56.1142534))      // Sets the center of the map to location user
                .zoom(14)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        gmap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

}