package com.silive.pc.roundtable;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    EditText signUpEmail, signUpPassword;
    Button signUpButton;
    String responseMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.sign_up_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        signUpEmail = (EditText) findViewById(R.id.sign_up_email);
        signUpPassword = (EditText) findViewById(R.id.sign_up_password);
    }

    private void userSignUp() {
        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing Up...");
        progressDialog.show();

        String email = signUpEmail.getText().toString().trim();
        String password = signUpPassword.getText().toString().trim();

        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        //Defining the user object as we need to pass it with the call
        User user = new User(email, password);

        //defining the call
        Call<ResponseBody> call = service.createUser(user);

        //calling the api
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //hiding progress dialog
                progressDialog.dismiss();

                try {
                     responseMessage = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //displaying the message from the response as toast
                Toast.makeText(getApplicationContext(), responseMessage, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    // method called when button is clicked
    public void SignUp(View view) {
        userSignUp();
    }


}
