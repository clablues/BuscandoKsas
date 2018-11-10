package com.example.claudioaldecosea.buscandoksas.model.fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.ToggleButton;
import android.widget.VideoView;

import com.example.claudioaldecosea.buscandoksas.R;

public class HelpVideo extends Fragment {

    private OnFragmentInteractionListener mListener;
    VideoView videoView;
    MediaController mediaController;

    public HelpVideo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String path = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.bunny2;

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_help_video, container, false);
        videoView = layout.findViewById(R.id.videoView);
        videoView.setVideoURI(Uri.parse(path));

        mediaController = new MediaController(getContext());

        videoView.setMediaController(mediaController);
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                mediaController.show();
            }
        });
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
