package com.silive.pc.roundtable.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.silive.pc.roundtable.ProfileImageAssets;
import com.silive.pc.roundtable.R;

import static android.content.Context.MODE_PRIVATE;
import static com.silive.pc.roundtable.activities.LogInActivity.LOG_IN_PREFS_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    private ImageView profileBackgroundImage, profileImage;
    private TextView profileUserName, profileUserEmail;

    private String userName, userEmail;
    private int userAvatarName;
    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        profileUserName = (TextView) rootView.findViewById(R.id.profile_user_name);
        profileUserEmail = (TextView) rootView.findViewById(R.id.profile_user_email);
        profileBackgroundImage = (ImageView) rootView.findViewById(R.id.profile_backround);
        profileImage = (ImageView) rootView.findViewById(R.id.profile_image);

        SharedPreferences prefs = getContext().getSharedPreferences(LOG_IN_PREFS_NAME, MODE_PRIVATE);
        userName = prefs.getString("userName", "No token generated");
        userEmail = prefs.getString("userEmail", "No token generated");
        userAvatarName = prefs.getInt("userAvatar", 0);

        // name, email, profileImage
        profileUserName.setText(userName);
        profileUserEmail.setText(userEmail);
        profileImage.setImageResource(ProfileImageAssets.getProfileImages().get(userAvatarName));


        return rootView;
    }

}
