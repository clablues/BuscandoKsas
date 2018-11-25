package com.example.claudioaldecosea.buscandoksas.model.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Switch;

import com.example.claudioaldecosea.buscandoksas.R;
import com.github.florent37.androidslidr.Slidr;

public class FilterSearch extends DialogFragment {

    private Slidr priceBar;
    private Switch hasGrill;
    private Switch hasGarage;
    private Button applyFilter;
    private Button closeFilter;
    private View layout;
    private NumberPicker roomNumber;
    Intent userFilterSearch = new Intent();

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
            /*
            int width = WindowManager.LayoutParams.MATCH_PARENT;
            int height = WindowManager.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            */
            //this line is what you need:
            //dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            Window window = dialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        }

        priceBar = dialog.findViewById(R.id.slidr);
        priceBar.setTextFormatter(new UruguayTextFormatter());
        priceBar.setMax(1000000);
        priceBar.addStep(new Slidr.Step("media", 500000, Color.parseColor("#405959"), Color.parseColor("#405959")));
        priceBar.setTextMax("max\nvalue");

        String userPriceInput = getArguments().getString("price");
        if (userPriceInput != null) {
            priceBar.setCurrentValue(Integer.valueOf(userPriceInput));
        }else{
            priceBar.setCurrentValue(0);
        }

        priceBar.setListener(new Slidr.Listener() {
            @Override
            public void valueChanged(Slidr slidr, float currentValue) {
            }

            @Override
            public void bubbleClicked(Slidr slidr) {
            }
        });

        hasGrill = dialog.findViewById(R.id.switch_parrillero);
        hasGarage = dialog.findViewById(R.id.switch_garage);
        roomNumber = dialog.findViewById(R.id.numberpicker);
        roomNumber.setMinValue(0);
        roomNumber.setMaxValue(10);

        // Fetch arguments from bundle

        Boolean hasGarageValue = Boolean.valueOf(getArguments().getString("hasGarageUserInput"));
        Boolean hasGrillValue = Boolean.valueOf(getArguments().getString("hasGrillUserInput"));

        //TODO Aca setear el valor que me venga en el getArguments para el NumberPicker

        hasGarage.setChecked(hasGarageValue);
        hasGrill.setChecked(hasGrillValue);

        roomNumber.setValue(getArguments().getInt("bedroomsQty"));

        applyFilter = dialog.findViewById(R.id.apply_filter_button);
        applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Aca tengo los datos que el usuario coloco en el filtro
                //los tengo que guardar y pasar al fragment de HouseList o a la activity

                String hasGrillUserInput = String.valueOf(hasGrill.isChecked());
                String hasGarageUserInput = String.valueOf(hasGarage.isChecked());

                String price = String.valueOf((int) priceBar.getCurrentValue());
                userFilterSearch.putExtra("price", price);

                if (hasGrill.isChecked()) {
                    userFilterSearch.putExtra("hasGrillUserInput", hasGrillUserInput);
                }

                if (hasGarage.isChecked()) {
                    userFilterSearch.putExtra("hasGarageUserInput", hasGarageUserInput);
                }

                if(roomNumber.getValue() != 0){
                    userFilterSearch.putExtra("bedroomsQty", roomNumber.getValue());
                }

                //En la busqueda por filtro no aplica el barrio
                userFilterSearch.putExtra("barrio", "");
                //TODO Ver si esta linea es necesaria...
                userFilterSearch.putExtra("mode", "search");

                getTargetFragment().onActivityResult(1, 200, userFilterSearch);
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

    public class UruguayTextFormatter implements Slidr.TextFormatter {

        @Override
        public String format(float value) {
            return String.format("%d $U", (int) value);
        }
    }
}