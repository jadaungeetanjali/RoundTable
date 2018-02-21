package com.silive.pc.roundtable.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.silive.pc.roundtable.R;

public class AddUserActivity extends AppCompatActivity {

    ImageView profileImageView;
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

            }

        });

    }
}
