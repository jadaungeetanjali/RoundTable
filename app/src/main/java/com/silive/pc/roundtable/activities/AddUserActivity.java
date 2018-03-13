package com.silive.pc.roundtable.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.silive.pc.roundtable.ProfileImageAssets;
import com.silive.pc.roundtable.R;
import com.silive.pc.roundtable.models.AddUserModel;
import com.silive.pc.roundtable.models.User;
import com.silive.pc.roundtable.services.APIService;
import com.silive.pc.roundtable.services.ServiceGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.silive.pc.roundtable.activities.LogInActivity.LOG_IN_PREFS_NAME;

public class AddUserActivity extends AppCompatActivity {

    ImageView addUserProfileImageView;
    EditText etAddUserName, etAddUserEmail;

    private List<Integer> mImageIds;
    private int mListIndex;
    private String addUserEmail, addUsername, addUserAvatarName, addUserAvatarColor = "";
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.add_user_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etAddUserName = (EditText) findViewById(R.id.add_user_name);
        etAddUserEmail = (EditText) findViewById(R.id.add_user_email);
        addUserProfileImageView = (ImageView) findViewById(R.id.add_user_profile_image);
        TextView addUserTextView = (TextView) findViewById(R.id.add_color_textview);


        addUserProfileImageView.setOnClickListener(new View.OnClickListener() {
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
                mListIndex = data.getIntExtra("position", 0);
                String avatarResourceName = getResources().getResourceName(mImageIds.get(mListIndex));
                addUserAvatarName = avatarResourceName.substring(avatarResourceName.lastIndexOf("/") + 1);
                Toast.makeText(this, "add user activity clicked resources name"+ addUserAvatarName, Toast.LENGTH_SHORT).show();
                if (mImageIds != null) {
                    // Set the image resource to the list item at the stored index
                    addUserProfileImageView.setImageResource(mImageIds.get(mListIndex));
                }
            }
        }
    }

    private void addUser() {
        //defining a progress dialog to show while logging in
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Add User...");
        progressDialog.show();

        addUsername = etAddUserName.getText().toString().trim();
        addUserEmail = etAddUserEmail.getText().toString().trim();

        SharedPreferences prefs = getSharedPreferences(LOG_IN_PREFS_NAME, MODE_PRIVATE);
        token = prefs.getString("token", "No token generated");//"No token generated" is the default value.


        //Defining retrofit api service
        APIService service = ServiceGenerator.createService(APIService.class);

        //Defining the add user object as we need to pass it with the call
        AddUserModel addUser = new AddUserModel(addUsername, addUserEmail, addUserAvatarName, addUserAvatarColor);

        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        map.put("Authorization", "Bearer "+token);
        //defining the call
        Call<AddUserModel> call = service.addUser(addUser, map);

        call.enqueue(new Callback<AddUserModel>() {
            @Override
            public void onResponse(Call<AddUserModel> call, Response<AddUserModel> response) {
                //hiding progress dialog
                progressDialog.dismiss();

                if (response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"id"+ response.body().getId(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AddUserActivity.this, HomeActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<AddUserModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addUserClicked(View view) {
        addUser();
    }


}
