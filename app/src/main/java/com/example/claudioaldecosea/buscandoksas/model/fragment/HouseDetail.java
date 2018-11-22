package com.example.claudioaldecosea.buscandoksas.model.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.claudioaldecosea.buscandoksas.R;
import com.example.claudioaldecosea.buscandoksas.domain.Habitaciones;
import com.example.claudioaldecosea.buscandoksas.domain.House;
import com.example.claudioaldecosea.buscandoksas.model.adapter.HouseDetailAdapter;
import com.example.claudioaldecosea.buscandoksas.model.adapter.RecycleViewClickListener;
import com.example.claudioaldecosea.buscandoksas.model.asynctask.AddToFavoritesAsyncTask;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HouseDetail extends Fragment implements OnMapReadyCallback, LoaderManager.LoaderCallbacks<Boolean> {

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
    private Menu menu;

    private FacebookLogin.FacebookLoginListener facebookListener;

    private OnFragmentInteractionListener mListener;

    public HouseDetail() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_house_detail, container, false);

        setHasOptionsMenu(true);

        Bundle bundle = this.getArguments();

        if(bundle != null){
            // handle your code here.
            mData = (House) this.getArguments().get(EXTRA_DATA);
        }

        barrio = layout.findViewById(R.id.txt_house_detail_barrio);
        precio = layout.findViewById(R.id.txt_house_detail_precio);
        cuartos = layout.findViewById(R.id.txt_house_detail_cant_dormitorios);
        metraje = layout.findViewById(R.id.txt_house_detail_metraje);

        banios = layout.findViewById(R.id.txt_house_detail_banos);
        garage = layout.findViewById(R.id.txt_house_detail_garage);
        parrillero = layout.findViewById(R.id.txt_house_detail_parrillero);
        balcon = layout.findViewById(R.id.txt_house_detail_balcon);
        patio = layout.findViewById(R.id.txt_house_detail_patio);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }


        mapView = layout.findViewById(R.id.mapView);
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

        mRecycleDetailView = layout.findViewById(R.id.images_list);
        mHouseDetailAdapter = new HouseDetailAdapter(getActivity(), mData.getFotos(), recycleViewListener);
        mRecycleDetailView.setAdapter(mHouseDetailAdapter);

        mRecycleDetailView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        return layout;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    //OnMapReadyCallback override methods

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
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FacebookLogin.FacebookLoginListener) {
            facebookListener = (FacebookLogin.FacebookLoginListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FacebookLoginListener");
        }
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        if(facebookListener.isFacebookLoggedIn()) {
            this.menu = menu;
            inflater.inflate(R.menu.activity_bar_menu_house_detail, menu);
            if (mData.getFavorito().equals("true")) {
                MenuItem isFavorite = menu.findItem(R.id.got_favorites_bar);
                MenuItem addFavorite = menu.findItem(R.id.add_favorites_bar);
                isFavorite.setVisible(true);
                addFavorite.setVisible(false);
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_favorites_bar:
                //Aca lo agrego a favoritos
                item.setVisible(false);
                MenuItem isFavorite = menu.findItem(R.id.got_favorites_bar);
                isFavorite.setVisible(true);
                startTask();
                return true;
            case R.id.got_favorites_bar:
                //Aca lo saco de favoritos
                item.setVisible(false);
                MenuItem addFavorite = menu.findItem(R.id.add_favorites_bar);
                addFavorite.setVisible(true);
                startTask();
                return true;
            default:
                break;
        }

        return false;
    }

    //***************** Loader interface implementation **************************

    private void startTask() {
        Bundle queryBundle = new Bundle();
        getActivity().getSupportLoaderManager().restartLoader(0, queryBundle, this);
    }


    @NonNull
    @Override
    public Loader<Boolean> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new AddToFavoritesAsyncTask(getContext(), Integer.valueOf(mData.getInmuebleId()));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Boolean> loader, Boolean addedToFavorites) {
        //En caso de fallar el agregado a favoritos, vuelvo a quitar el corazon de favoritos
        if (!addedToFavorites){
            MenuItem isFavorite = menu.findItem(R.id.got_favorites_bar);
            isFavorite.setVisible(false);
            MenuItem addFavorite = menu.findItem(R.id.add_favorites_bar);
            addFavorite.setVisible(true);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Boolean> loader) {

    }
}
