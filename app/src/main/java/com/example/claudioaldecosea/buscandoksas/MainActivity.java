package com.example.claudioaldecosea.buscandoksas;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.claudioaldecosea.buscandoksas.model.fragment.FacebookLogin;
import com.example.claudioaldecosea.buscandoksas.model.fragment.HelpVideo;
import com.example.claudioaldecosea.buscandoksas.model.fragment.HouseList;
import com.example.claudioaldecosea.buscandoksas.model.fragment.TermsAndConditions;
import com.facebook.AccessToken;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FacebookLogin.FacebookLoginListener {

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

        getSupportFragmentManager().beginTransaction().add(R.id.home_fragments_container, new HouseList()).commit();
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
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragments_container, new FacebookLogin()).addToBackStack(null).commit();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.logout:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragments_container, new FacebookLogin()).addToBackStack(null).commit();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragments_container, new HouseList()).commit();
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
        //Controlo por las dudas que este logeado aunque en principio me van a llamar desde el onSuccess del FacebookLogin
        MenuItem favoritos = navigationView.getMenu().findItem(R.id.favoritos);
        favoritos.setVisible(true);
        //TODO Ver si con esto refresco el menu
        navigationView.refreshDrawableState();
    }

    @Override
    public void hideFavorites() {
        //Controlo por las dudas que este logeado aunque en principio me van a llamar desde el onSuccess del FacebookLogin
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_bar_menu, menu);

        return true;
    }
}
