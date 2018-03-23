package com.silive.pc.roundtable.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.silive.pc.roundtable.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChannelFragment extends Fragment {


    public ChannelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       final View rootView = inflater.inflate(R.layout.fragment_channel, container, false);

        // Inflate the layout for this fragment
        return rootView;
    }

}
