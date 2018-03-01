package com.silive.pc.roundtable.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.silive.pc.roundtable.ProfileImageAssets;
import com.silive.pc.roundtable.R;

import java.util.List;

public class AddUserActivity extends AppCompatActivity {

    ImageView profileImageView;

    private List<Integer> mImageIds;
    private int mListIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.add_user_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profileImageView = (ImageView) findViewById(R.id.add_user_profile_image);
        TextView addUserTextView = (TextView) findViewById(R.id.add_color_textview);
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddUserActivity.this, ProfileImagesActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                mImageIds = ProfileImageAssets.getProfileImages();
                mListIndex = data.getIntExtra("result", 0);
                Toast.makeText(this, "add user activity result", Toast.LENGTH_SHORT).show();
                if (mImageIds != null) {
                    // Set the image resource to the list item at the stored index
                    profileImageView.setImageResource(mImageIds.get(mListIndex));
                }
                }
        }
    }
}
