package com.example.claudioaldecosea.buscandoksas.model.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;

import com.example.claudioaldecosea.buscandoksas.R;

public class FilterSearch extends DialogFragment implements View.OnClickListener {

    private SeekBar priceBar;
    private Switch hasGrill;
    private Switch hasGarage;
    private Button applyFilter;
    private Button closeFilter;
    private View layout;
    private Button one_room_btn;
    private Button two_room_btn;
    private Button three_room_btn;
    private Button four_room_btn;

    public FilterSearch() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static FilterSearch newInstance(String title, Bundle searchData) {
        FilterSearch frag = new FilterSearch();
        searchData.putString("title", title);
        frag.setArguments(searchData);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_filter_search, container);
        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }

        priceBar = dialog.findViewById(R.id.seekBar);
        hasGrill = dialog.findViewById(R.id.switch_parrillero);
        hasGarage = dialog.findViewById(R.id.switch_garage);

        one_room_btn = dialog.findViewById(R.id.one_room);
        one_room_btn.setOnClickListener(this); // calling onClick() method
        two_room_btn = dialog.findViewById(R.id.two_room);
        two_room_btn.setOnClickListener(this);
        three_room_btn = dialog.findViewById(R.id.three_room);
        three_room_btn.setOnClickListener(this);
        four_room_btn = dialog.findViewById(R.id.four_room);
        four_room_btn.setOnClickListener(this);

        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        Boolean hasGarageValue = getArguments().getBoolean("hasGarageUserInput");
        Boolean hasGrillValue = getArguments().getBoolean("hasGrillUserInput");
        getDialog().setTitle(title);
        hasGarage.setChecked(hasGarageValue);
        hasGrill.setChecked(hasGrillValue);

        applyFilter = dialog.findViewById(R.id.apply_filter_button);
        applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Aca tengo los datos que el usuario coloco en el filtro
                //los tengo que guardar y pasar al fragment de HouseList o a la activity
                Boolean hasGrillUserInput = hasGrill.isChecked();
                Boolean hasGarageUserInput = hasGarage.isChecked();
                Intent userFilterSearch = new Intent();
                userFilterSearch.putExtra("hasGrillUserInput",hasGrillUserInput);
                userFilterSearch.putExtra("hasGarageUserInput", hasGarageUserInput);

                getTargetFragment().onActivityResult(1,200,userFilterSearch);
                dismiss();
            }
        });

        closeFilter = dialog.findViewById(R.id.close_filter_button);
        closeFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_filter_search, null);

        builder.setView(view);
        Dialog dialog = builder.create();

        dialog.getWindow().getAttributes().alpha = 1f;

        return dialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.one_room:
                one_room_btn.setSelected(!one_room_btn.isSelected());
                two_room_btn.setSelected(false);
                three_room_btn.setSelected(false);
                four_room_btn.setSelected(false);
                break;

            case R.id.two_room:
                two_room_btn.setSelected(!two_room_btn.isSelected());
                one_room_btn.setSelected(false);
                three_room_btn.setSelected(false);
                four_room_btn.setSelected(false);
                break;

            case R.id.three_room:
                three_room_btn.setSelected(!three_room_btn.isSelected());
                one_room_btn.setSelected(false);
                two_room_btn.setSelected(false);
                four_room_btn.setSelected(false);
                break;

            case R.id.four_room:
                four_room_btn.setSelected(!four_room_btn.isSelected());
                one_room_btn.setSelected(false);
                two_room_btn.setSelected(false);
                three_room_btn.setSelected(false);
                break;

            default:
                break;
        }
    }
}