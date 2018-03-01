package com.silive.pc.roundtable.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.silive.pc.roundtable.ProfileImageAssets;
import com.silive.pc.roundtable.R;
import com.silive.pc.roundtable.fragments.ProfileImagesFragment;

public class ProfileImagesActivity extends AppCompatActivity implements ProfileImagesFragment.OnImageClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_images);

        if (savedInstanceState == null){
            ProfileImagesFragment profileImagesFragment = new ProfileImagesFragment();

            // add the fragment to its container using FragmentManager and its transaction
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.profile_images_container, profileImagesFragment)
                    .commit();
        }
    }

    // Define the behavior for onImageSelected
    @Override
    public void onImageSelected(int position) {

        // Create a Toast that displays the position that was clicked
        Toast.makeText(this, "position clicked" + position, Toast.LENGTH_SHORT).show();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", position);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
