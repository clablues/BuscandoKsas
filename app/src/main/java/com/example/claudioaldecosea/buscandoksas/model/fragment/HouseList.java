package com.example.claudioaldecosea.buscandoksas.model.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.claudioaldecosea.buscandoksas.R;
import com.example.claudioaldecosea.buscandoksas.domain.House;
import com.example.claudioaldecosea.buscandoksas.model.adapter.HouseListAdapter;
import com.example.claudioaldecosea.buscandoksas.model.adapter.RecycleViewClickListener;
import com.example.claudioaldecosea.buscandoksas.model.asynctask.GetHousesAsyncTask;

import java.util.ArrayList;

public class HouseList extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<House>> {
    private RecyclerView mHouseListRecycleView;
    private HouseListAdapter mHouseListAdapter;
    private ArrayList<House> mHousesList = new ArrayList<>();
    private String mode = "";
    boolean isDualPane;
    int mSelectedPosition = 0;
    private MenuItem search;
    private static final String MODE_SEARCH = "search";
    private String userSearch;
    private MenuItem filter;
    private static final int REQ_CODE_FILTER_SEARCH = 3;
    Bundle dataSearchBundle = new Bundle();

    public HouseList() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.home_fragment_house_list, container, false);
        //Obtengo la referencia a la recycleView que lista las casas en la home
        mHouseListRecycleView = layout.findViewById(R.id.houses_recycle_view);

        Bundle bundle = this.getArguments();

        setHasOptionsMenu(true);

        if (bundle != null) {
            mode = this.getArguments().getString("mode");
        }

        RecycleViewClickListener recycleViewListener = new RecycleViewClickListener() {
            @Override
            public void onClick(View v, int pos) {
                Bundle bundle = new Bundle();
                House currentHouse = mHousesList.get(pos);
                bundle.putParcelable(HouseDetail.EXTRA_DATA, currentHouse);

                HouseDetail houseDetail = new HouseDetail();
                houseDetail.setArguments(bundle);

                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.house_detail_land_fragment, houseDetail).commit();
                } else {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.home_fragments_container, houseDetail)
                            .addToBackStack(null)
                            .commit();
                }
            }
        };

        //Creo el adapter para listar las casas en la recycleView anterior
        mHouseListAdapter = new HouseListAdapter(getContext(), mHousesList, recycleViewListener);
        //Seteo la recycle view al adapter
        mHouseListRecycleView.setAdapter(mHouseListAdapter);

        //Creo un LinearLayout para especificarle como mostrar la informacion en la recycleView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mHouseListRecycleView.setLayoutManager(linearLayoutManager);

        startTask();

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

    //Sobre escritura de metodos por implementar la interfasz Loader
    //Ver en este caso como es el tema ya que las imagenes se cargan de una sin interaccion del usuario.
    //Tampoco tengo parametros para pasar en el bundle.
    private void startTask() {
        Bundle queryBundle = new Bundle();
        getActivity().getSupportLoaderManager().restartLoader(0, queryBundle, this);
    }

    @NonNull
    @Override
    public Loader<ArrayList<House>> onCreateLoader(int i, @Nullable Bundle bundle) {
        GetHousesAsyncTask housesAsyncTask = new GetHousesAsyncTask(getContext(), mode);
        if (mode == MODE_SEARCH) {
            housesAsyncTask.setExtraParams(this.getArguments());
            return housesAsyncTask;
        }

        return housesAsyncTask;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<House>> loader, ArrayList<House> houses) {
        mHousesList.clear();
        if (houses != null) {
            this.mHousesList.addAll(houses);
            mHouseListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<House>> loader) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.activity_bar_menu_house_list, menu);

        //Obtengo la referencia al SearchView
        search = menu.findItem(R.id.search);
        SearchView sv = (SearchView) search.getActionView();

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String userInput = new String(query.getBytes());
                dataSearchBundle.putString("mode", MODE_SEARCH);
                dataSearchBundle.putString("userInput", userInput);
                HouseList houseList = new HouseList();
                houseList.setArguments(dataSearchBundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragments_container, houseList).commit();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        filter = menu.findItem(R.id.filter);

        filter.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                showEditDialog();
                return true;
            }
        });
    }

    private void showEditDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FilterSearch filterSearch = FilterSearch.newInstance("Filter Search", dataSearchBundle);
        filterSearch.setTargetFragment(this, REQ_CODE_FILTER_SEARCH);
        filterSearch.show(fm, "fragment_edit_name");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dataSearchBundle = data.getExtras();
    }
}
