package com.silive.pc.roundtable.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;
import com.silive.pc.roundtable.services.APIService;
import com.silive.pc.roundtable.MyTextWatcher;
import com.silive.pc.roundtable.R;
import com.silive.pc.roundtable.services.ServiceGenerator;
import com.silive.pc.roundtable.models.User;
import com.silive.pc.roundtable.Validation;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private static String email, password;
    private EditText signUpEmail, signUpPassword;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;
    String responseMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.sign_up_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        inputLayoutEmail = (TextInputLayout) findViewById(R.id.sign_up_input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.sign_up_input_layout_password);
        signUpEmail =  findViewById(R.id.sign_up_input_email);
        signUpPassword = findViewById(R.id.sign_up_input_password);


        signUpEmail.addTextChangedListener(new MyTextWatcher(signUpEmail, inputLayoutEmail));
        signUpPassword.addTextChangedListener(new MyTextWatcher(signUpPassword, inputLayoutPassword));
    }

    private void userSignUp() {
        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing Up...");
        progressDialog.show();

        email = signUpEmail.getText().toString().trim();
        password = signUpPassword.getText().toString().trim();


        //Defining retrofit api service
        APIService service = ServiceGenerator.createService(APIService.class);

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

                if (response.isSuccessful()) {
                    try {
                        responseMessage = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Log.i("response message", responseMessage);
                    //displaying the message from the response as toast
                    TastyToast.makeText(getApplicationContext(), responseMessage, Toast.LENGTH_LONG,TastyToast.SUCCESS).show();
                    Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                    startActivity(intent);
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
