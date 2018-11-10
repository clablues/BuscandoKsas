package com.example.claudioaldecosea.buscandoksas;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.claudioaldecosea.buscandoksas.domain.House;
import com.example.claudioaldecosea.buscandoksas.model.adapter.HouseListAdapter;
import com.example.claudioaldecosea.buscandoksas.model.adapter.RecycleViewClickListener;
import com.example.claudioaldecosea.buscandoksas.model.asynctask.GetHousesAsyncTask;
import com.example.claudioaldecosea.buscandoksas.model.fragment.FacebookLoginFragment;
import com.example.claudioaldecosea.buscandoksas.model.fragment.FacebookLoginListener;
import com.example.claudioaldecosea.buscandoksas.model.fragment.HelpVideo;
import com.example.claudioaldecosea.buscandoksas.model.fragment.HouseListFragment;
import com.example.claudioaldecosea.buscandoksas.model.fragment.TermsAndConditions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FacebookLoginListener {

    private Toolbar toolBar;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolBar, R.string.open_nav, R.string.close_nav);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Al levantar la aplicacion chequeo si estoy logeado y muestro los favoritos asi como cambio el texto del boton a Logout.
        if (isFacebookLoggedIn()) {
            MenuItem favoritos = navigationView.getMenu().findItem(R.id.favoritos);
            favoritos.setVisible(true);
            showLogout();
        }else{
            showLogin();
        }

        getSupportFragmentManager().beginTransaction().add(R.id.home_fragments_container, new HouseListFragment()).commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        Menu m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);
            if (!(mi.getItemId() == menuItem.getItemId())) {
                mi.setCheckable(false);
            }
        }

        menuItem.setCheckable(true);
        menuItem.setChecked(true);

        switch (menuItem.getItemId()) {
            case R.id.login:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragments_container, new FacebookLoginFragment()).addToBackStack(null).commit();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.logout:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragments_container, new FacebookLoginFragment()).addToBackStack(null).commit();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragments_container, new HouseListFragment()).commit();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.terms:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragments_container, new TermsAndConditions()).addToBackStack(null).commit();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.help:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragments_container, new HelpVideo()).addToBackStack(null).commit();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            default:
                return true;
        }
    }

    public boolean isFacebookLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        return isLoggedIn;
    }

    @Override
    public void showFavorites() {
        //Controlo por las dudas que este logeado aunque en principio me van a llamar desde el onSuccess del FacebookLoginFragment
        MenuItem favoritos = navigationView.getMenu().findItem(R.id.favoritos);
        favoritos.setVisible(true);
        //TODO Ver si con esto refresco el menu
        navigationView.refreshDrawableState();
    }

    @Override
    public void hideFavorites() {
        //Controlo por las dudas que este logeado aunque en principio me van a llamar desde el onSuccess del FacebookLoginFragment
        MenuItem favoritos = navigationView.getMenu().findItem(R.id.favoritos);
        favoritos.setVisible(false);
        navigationView.refreshDrawableState();
    }

    @Override
    public void showLogin() {
        MenuItem login = navigationView.getMenu().findItem(R.id.login);
        login.setVisible(true);
        MenuItem logout = navigationView.getMenu().findItem(R.id.logout);
        logout.setVisible(false);
        navigationView.refreshDrawableState();
    }

    @Override
    public void showLogout() {
        MenuItem logout = navigationView.getMenu().findItem(R.id.logout);
        logout.setVisible(true);
        MenuItem login = navigationView.getMenu().findItem(R.id.login);
        login.setVisible(false);
        navigationView.refreshDrawableState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
