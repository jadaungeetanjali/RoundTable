package com.silive.pc.roundtable;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity {

    EditText etLogInEmail, etLogInPassword;

    private String logInEmail, logInpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.log_in_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etLogInEmail = findViewById(R.id.log_in_input_email);
        etLogInPassword = findViewById(R.id.log_in_input_password);
    }

    private void userLogin() {
        //defining a progress dialog to show while logging in
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing Up...");
        progressDialog.show();

        logInEmail = etLogInEmail.getText().toString().trim();
        logInpassword = etLogInPassword.getText().toString().trim();

        //Defining retrofit api service
        APIService service = ServiceGenerator.createService(APIService.class);

        //Defining the user object as we need to pass it with the call
        User user = new User(logInEmail, logInpassword);

        //defining the call
        Call<User> call = service.userLogin(user);

        //calling the api
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                //hiding progress dialog
                progressDialog.dismiss();

                if (response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), response.body().getUsername(), Toast.LENGTH_SHORT).show();
                    //start new activity
                }else{
                    Toast.makeText(getApplicationContext(), "Invalid email or password", Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void LogIn(View view) {
        userLogin();
    }


}
