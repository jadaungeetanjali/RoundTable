package com.silive.pc.roundtable.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.silive.pc.roundtable.R;
import com.silive.pc.roundtable.RoundTableApplication;
import com.github.nkzawa.socketio.client.Socket;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChannelFragment extends Fragment {

    private static final String TAG = "ChannelFragment";

    private String channelName, channelDescription;
    private Socket mSocket;

    private Boolean isConnected = true;

    public ChannelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_channel, container, false);

        return rootView;
    }


}
