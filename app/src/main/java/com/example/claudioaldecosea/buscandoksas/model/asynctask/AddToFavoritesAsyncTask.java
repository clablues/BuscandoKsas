package com.example.claudioaldecosea.buscandoksas.model.asynctask;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.claudioaldecosea.buscandoksas.model.network.NetworkUtil;

public class AddToFavoritesAsyncTask extends AsyncTaskLoader<Boolean> {

    private static final String AGREGAR_FAVORITOS = "http://173.233.86.183:8080/CursoAndroidWebApp/rest/guardarFavorito";
    private int houseId;
    private String userId;

    public AddToFavoritesAsyncTask(Context context, int houseId, String facebookUserId ) {
        super(context);
        this.houseId = houseId;
        this.userId = facebookUserId;
    }

    public Boolean loadInBackground() {
        NetworkUtil networkUtil = new NetworkUtil(AGREGAR_FAVORITOS);
        networkUtil.setAuthorization(userId);
        String postData = "{\"InmuebleId\": "+ houseId + "}";
        return networkUtil.addToFavorites(postData);
    }

    @Override
    protected void onStartLoading() {
        forceLoad(); // ejecuta loadInBackgroud
    }
}
