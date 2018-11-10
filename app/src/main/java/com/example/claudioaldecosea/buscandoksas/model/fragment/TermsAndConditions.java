package com.example.claudioaldecosea.buscandoksas.model.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.claudioaldecosea.buscandoksas.R;

public class TermsAndConditions extends Fragment {

    private String TERMS_URL =  "http://173.233.86.183:8080/CursoAndroidWebApp/condiciones.html";

    private OnFragmentInteractionListener mListener;
    private WebView webView;

    public TermsAndConditions() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.fragment_terms_and_conditions, container, false);
        webView = layout.findViewById(R.id.web_view_terms);
        webView.loadUrl(TERMS_URL);
        return layout;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
