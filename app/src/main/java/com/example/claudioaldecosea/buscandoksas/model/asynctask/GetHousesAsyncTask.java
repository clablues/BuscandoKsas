package com.example.claudioaldecosea.buscandoksas.model.asynctask;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;

import com.example.claudioaldecosea.buscandoksas.domain.House;
import com.example.claudioaldecosea.buscandoksas.model.network.NetworkUtil;

import java.util.ArrayList;

public class GetHousesAsyncTask extends AsyncTaskLoader<ArrayList<House>> {

    public GetHousesAsyncTask(Context context) {
        super(context);
    }

    @Override
    public ArrayList<House> loadInBackground() {
        String url = "http://173.233.86.183:8080/CursoAndroidWebApp/rest/buscarInmueble";
        NetworkUtil netUtils = new NetworkUtil(url);
        ArrayList<House> houses = netUtils.getHouses();
        return houses;
    }

    @Override
    protected void onStartLoading() {
        forceLoad(); // ejecuta loadInBackgroud
    }
}
