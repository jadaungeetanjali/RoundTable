package com.silive.pc.roundtable;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    private EditText signUpEmail, signUpPassword, signUpUsername;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword;
    String responseMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.sign_up_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inputLayoutName = (TextInputLayout) findViewById(R.id.sign_up_input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.sign_up_input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.sign_up_input_layout_password);
        signUpUsername =  findViewById(R.id.sign_up_input_name);
        signUpEmail =  findViewById(R.id.sign_up_input_email);
        signUpPassword = findViewById(R.id.sign_up_input_password);

    }

    private void userSignUp() {
        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing Up...");
        progressDialog.show();

        String username = signUpUsername.getText().toString().trim();
        String email = signUpEmail.getText().toString().trim();
        String password = signUpPassword.getText().toString().trim();


        //Defining retrofit api service
        APIService service = ServiceGenerator.createService(APIService.class);

        //Defining the user object as we need to pass it with the call
        User user = new User(email, password, username);

        //defining the call
        Call<ResponseBody> call = service.createUser(user);

        //calling the api
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //hiding progress dialog
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    try {
                        responseMessage = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Log.i("response message", responseMessage);
                    //displaying the message from the response as toast
                    Toast.makeText(getApplicationContext(), responseMessage, Toast.LENGTH_LONG).show();
                }else {
                    // error case
                    switch (response.code()) {
                        case 404:
                            Toast.makeText(getApplicationContext(), "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 503:
                            Toast.makeText(getApplicationContext(), "Service Unavailable", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(getApplicationContext(), "User exists already", Toast.LENGTH_SHORT).show();
                        default:
                            Toast.makeText(getApplicationContext(), "unknown error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
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
