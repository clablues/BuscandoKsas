package com.example.claudioaldecosea.buscandoksas.model.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.Switch;

import com.example.claudioaldecosea.buscandoksas.R;

public class FilterSearch extends DialogFragment {

    private SeekBar priceBar;
    private Switch hasGrill;
    private Switch hasGarage;

    public FilterSearch() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static FilterSearch newInstance(String title) {
        FilterSearch frag = new FilterSearch();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL,
                android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter_search, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        priceBar = view.findViewById(R.id.seekBar);

        /*
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        */
        hasGrill = view.findViewById(R.id.switch_parrillero);
        hasGarage = view.findViewById(R.id.switch_garage);

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
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
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //String mDataRecieved = getArguments().getString(TITLE,"defaultTitle");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_filter_search, null);

        /*
        TextView mTextView = (TextView) view.findViewById(R.id.textview);
        mTextView.setText(mDataRecieved);
        setCancelable(false);
        */

        builder.setView(view);
        Dialog dialog = builder.create();

        dialog.getWindow().getAttributes().alpha = 1f;

        /*
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(R.id));
        */
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return dialog;

    }
}