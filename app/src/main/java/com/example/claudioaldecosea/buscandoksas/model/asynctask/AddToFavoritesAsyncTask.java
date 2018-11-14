package com.example.claudioaldecosea.buscandoksas.model.asynctask;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.claudioaldecosea.buscandoksas.model.network.NetworkUtil;

public class AddToFavoritesAsyncTask extends AsyncTaskLoader<Boolean> {

    private static final String AGREGAR_FAVORITOS = "http://173.233.86.183:8080/CursoAndroidWebApp/rest/guardarFavorito";
    private int houseId;

    public AddToFavoritesAsyncTask(Context context, int houseId ) {
        super(context);
        this.houseId = houseId;
    }

    public Boolean loadInBackground() {
        NetworkUtil networkUtil = new NetworkUtil(AGREGAR_FAVORITOS);
        String postData = "{\"InmuebleId\": "+ houseId + "}";
        return networkUtil.addToFavorites(postData);
    }

    @Override
    protected void onStartLoading() {
        forceLoad(); // ejecuta loadInBackgroud
    }
}
