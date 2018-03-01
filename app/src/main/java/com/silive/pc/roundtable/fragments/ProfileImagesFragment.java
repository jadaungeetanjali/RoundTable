package com.silive.pc.roundtable.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.silive.pc.roundtable.ProfileImageAssets;
import com.silive.pc.roundtable.ProfileImagesAdapter;
import com.silive.pc.roundtable.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileImagesFragment extends Fragment {


    // Define a new interface OnImageClickListener that triggers a callback in the host activity
    OnImageClickListener mCallBack;

    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
    public interface OnImageClickListener{
        void onImageSelected(int position);
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallBack = (OnImageClickListener) context;
    }

    public ProfileImagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       final View rootView = inflater.inflate(R.layout.fragment_profile_images, container, false);

       GridView gridView = (GridView) rootView.findViewById(R.id.profile_images_grid_view);
       ProfileImagesAdapter mAdapter = new ProfileImagesAdapter(getContext(), ProfileImageAssets.getProfileImages());
       gridView.setAdapter(mAdapter);

        // Set a click listener on the gridView and trigger the callback onImageSelected when an item is clicked
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Trigger the callback method and pass in the position that was clicked
                mCallBack.onImageSelected(position);
            }
        });


        return rootView;
    }

}
