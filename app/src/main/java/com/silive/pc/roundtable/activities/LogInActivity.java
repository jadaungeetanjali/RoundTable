package com.silive.pc.roundtable.activities;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.silive.pc.roundtable.services.APIService;
import com.silive.pc.roundtable.MyTextWatcher;
import com.silive.pc.roundtable.R;
import com.silive.pc.roundtable.services.ServiceGenerator;
import com.silive.pc.roundtable.models.User;
import com.silive.pc.roundtable.Validation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity {

    EditText etLogInEmail, etLogInPassword;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;

    private String logInEmail, logInpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.log_in_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inputLayoutEmail = (TextInputLayout) findViewById(R.id.log_in_input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.log_in_input_layout_password);
        etLogInEmail = findViewById(R.id.log_in_input_email);
        etLogInPassword = findViewById(R.id.log_in_input_password);

        etLogInEmail.addTextChangedListener(new MyTextWatcher(etLogInEmail, inputLayoutEmail));
        etLogInPassword.addTextChangedListener(new MyTextWatcher(etLogInPassword, inputLayoutPassword));
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
                    Toast.makeText(getApplicationContext(), response.body().getUser(), Toast.LENGTH_SHORT).show();
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

    private boolean checkValidation() {
        boolean validation = true;

        if (!Validation.isEmailAddress(etLogInEmail,inputLayoutEmail, true)) {
            validation = false;
        }
        if (!Validation.hasText(etLogInPassword, inputLayoutPassword)) {
            validation = false;
        }

        return validation;
    }
    public void LogIn(View view) {
        if(checkValidation()){
            userLogin();
        }else{
            Toast.makeText(getApplicationContext(), "Form Contains error", Toast.LENGTH_SHORT).show();
        }

    }


}
