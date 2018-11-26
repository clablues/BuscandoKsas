package com.example.claudioaldecosea.buscandoksas.model.asynctask;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.claudioaldecosea.buscandoksas.model.network.NetworkUtil;

public class HouseApiAsyncTask extends AsyncTaskLoader<Boolean> {
    private static final String LOGIN_API = "http://173.233.86.183:8080/CursoAndroidWebApp/rest/login";
    private String userId;

    public HouseApiAsyncTask(Context context, String facebookUserId) {
        super(context);
        this.userId = facebookUserId;
    }

    public Boolean loadInBackground() {
        NetworkUtil networkUtil = new NetworkUtil(LOGIN_API);
        networkUtil.setAuthorization(userId);
        String postData = "{\"email\":\"clablues@gmail.com\"}";
        return networkUtil.loginToHouseApi(postData);
    }

    @Override
    protected void onStartLoading() {
        forceLoad(); // ejecuta loadInBackgroud
    }
}
