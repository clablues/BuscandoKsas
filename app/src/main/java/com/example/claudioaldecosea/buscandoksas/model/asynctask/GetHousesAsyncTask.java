package com.example.claudioaldecosea.buscandoksas.model.asynctask;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;

import com.example.claudioaldecosea.buscandoksas.domain.House;
import com.example.claudioaldecosea.buscandoksas.model.network.NetworkUtil;

import java.util.ArrayList;

public class GetHousesAsyncTask extends AsyncTaskLoader<ArrayList<House>> {

    private String mode;
    private static final String BUSCAR_INMUEBLES = "http://173.233.86.183:8080/CursoAndroidWebApp/rest/buscarInmueble";
    private static final String LISTADO_FAVORITOS = "http://173.233.86.183:8080/CursoAndroidWebApp/rest/listadoFavoritos";
    private static final String MODE_HOME = "home";
    private static final String MODE_FAVORITES = "favorites";
    private static final String MODE_SEARCH = "search";

    public GetHousesAsyncTask(Context context, String mode) {
        super(context);
        this.mode = mode;
    }

    @Override
    public ArrayList<House> loadInBackground() {
        switch (mode) {
            case MODE_HOME: {
                NetworkUtil netUtils = new NetworkUtil(BUSCAR_INMUEBLES);
                String postData = "{\"MaxResults\":10,\"Barrio\":\"\",\"Precio\":\"\",\"CantDormitorio\":\"\",\"TieneParrillero\":\"\",\"TieneGarage\":\"\",\"TieneBalcon\":\"\",\"TienePatio\":\"\"}";
                ArrayList<House> houses = netUtils.getHouses(postData);
                return houses;
            }

            case MODE_FAVORITES: {
                String postData = "{}";
                NetworkUtil netUtils = new NetworkUtil(LISTADO_FAVORITOS);
                ArrayList<House> houses = netUtils.getHouses(postData);
                return houses;
            }

            case MODE_SEARCH:{
                NetworkUtil netUtils = new NetworkUtil(BUSCAR_INMUEBLES);
                String postData = "{\"MaxResults\":2,\"Barrio\":\"\",\"Precio\":\"\",\"CantDormitorio\":\"\",\"TieneParrillero\":\"\",\"TieneGarage\":\"\",\"TieneBalcon\":\"\",\"TienePatio\":\"\"}";
                ArrayList<House> houses = netUtils.getHouses(postData);
                return houses;
            }

            default: {
                NetworkUtil netUtils = new NetworkUtil(BUSCAR_INMUEBLES);
                String postData = "{\"MaxResults\":10,\"Barrio\":\"\",\"Precio\":\"\",\"CantDormitorio\":\"\",\"TieneParrillero\":\"\",\"TieneGarage\":\"\",\"TieneBalcon\":\"\",\"TienePatio\":\"\"}";
                ArrayList<House> houses = netUtils.getHouses(postData);
                return houses;
            }
        }
    }

    @Override
    protected void onStartLoading() {
        forceLoad(); // ejecuta loadInBackgroud
    }
}
