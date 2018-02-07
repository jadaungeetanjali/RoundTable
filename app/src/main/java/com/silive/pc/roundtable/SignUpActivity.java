package com.silive.pc.roundtable;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;

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

    private static String username, email, password;
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

        signUpUsername.addTextChangedListener(new MyTextWatcher(signUpUsername, inputLayoutName));
        signUpEmail.addTextChangedListener(new MyTextWatcher(signUpEmail, inputLayoutEmail));
        signUpPassword.addTextChangedListener(new MyTextWatcher(signUpPassword, inputLayoutPassword));
    }

    private void userSignUp() {
        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing Up...");
        progressDialog.show();

        username = signUpUsername.getText().toString().trim();
        email = signUpEmail.getText().toString().trim();
        password = signUpPassword.getText().toString().trim();


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
                    TastyToast.makeText(getApplicationContext(), responseMessage, Toast.LENGTH_LONG,TastyToast.SUCCESS).show();
                }else {
                    // error case
                    switch (response.code()) {
                        case 404:
                            TastyToast.makeText(getApplicationContext(), "not found", Toast.LENGTH_SHORT,TastyToast.CONFUSING).show();
                            break;
                        case 503:
                            TastyToast.makeText(getApplicationContext(), "Service Unavailable", Toast.LENGTH_SHORT,TastyToast.ERROR).show();
                            break;
                        case 500:
                            TastyToast.makeText(getApplicationContext(), "User exists already", Toast.LENGTH_SHORT, TastyToast.INFO).show();
                        default:
                            TastyToast.makeText(getApplicationContext(), "unknown error", Toast.LENGTH_SHORT, TastyToast.ERROR).show();
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

    private boolean checkValidation() {
        boolean validation = true;

        if (!Validation.hasText(signUpUsername, inputLayoutName)) {
            validation = false;
        }
        if (!Validation.isEmailAddress(signUpEmail,inputLayoutEmail, true)) {
            validation = false;
        }
        if (!Validation.hasText(signUpPassword, inputLayoutPassword)) {
            validation = false;
        }

        return validation;
    }
    // method called when button is clicked
    public void SignUp(View view) {
        if (checkValidation()) {
            userSignUp();
        }else {
            Toast.makeText(getApplicationContext(), "Form Contains error", Toast.LENGTH_SHORT).show();
        }
    }

}
