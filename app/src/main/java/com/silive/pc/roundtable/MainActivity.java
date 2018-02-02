package com.silive.pc.roundtable;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    Button logInButton, signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        logInButton = (Button) findViewById(R.id.main_login_button);
        signUpButton = (Button) findViewById(R.id.main_sign_up_button);
    }

    public void logInClicked(View view) {
    }

    public void signUpClicked(View view) {
        Toast.makeText(this, "signUp in main clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
}
