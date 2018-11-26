package com.example.claudioaldecosea.buscandoksas.model.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.claudioaldecosea.buscandoksas.R;
import com.example.claudioaldecosea.buscandoksas.model.asynctask.HouseApiAsyncTask;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class FacebookLogin extends Fragment implements LoaderManager.LoaderCallbacks<Boolean> {
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private FacebookLoginListener facebookListener;
    AccessTokenTracker accessTokenTracker;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null) {
                    facebookListener.hideFavorites();
                    facebookListener.showLogin();
                }
            }
        };

        accessTokenTracker.startTracking();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FacebookLoginListener) {
            facebookListener = (FacebookLoginListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FacebookLoginListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.facebook_login_fragment, container, false);
        callbackManager = CallbackManager.Factory.create();
        loginButton = layout.findViewById(R.id.login_button);

        loginButton.setReadPermissions("email");
        // If using in a fragment
        loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                facebookListener.showFavorites();
                facebookListener.showLogout();
                startTask();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }

    //******************** AsyncTaskLoader implement methods ********************

    private void startTask() {
        Bundle queryBundle = new Bundle();
        getActivity().getSupportLoaderManager().restartLoader(0, queryBundle, this);
    }

    @NonNull
    @Override
    public Loader<Boolean> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new HouseApiAsyncTask(getContext(),facebookListener.getFacebookUserId());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Boolean> loader, Boolean aBoolean) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Boolean> loader) {

    }

    //*******************************************************************************

    public interface FacebookLoginListener {

        boolean isFacebookLoggedIn();

        String getFacebookUserId();

        void showFavorites();

        void hideFavorites();

        void showLogin();

        void showLogout();
    }

}
