package com.silive.pc.roundtable.services;

import com.silive.pc.roundtable.models.AddUserModel;
import com.silive.pc.roundtable.models.User;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by PC on 2/2/2018.
 */

public interface APIService {


    // the Sign Up call
    @Headers("Content-Type: application/json")
    @POST("account/register")
    Call<ResponseBody> createUser(@Body User user);

    // the log in call
    @Headers("Content-Type: application/json")
    @POST("account/login")
    Call<User> userLogin(@Body User user);

    // the add User call
    @POST("user/add")
    Call<AddUserModel> addUser(@Body AddUserModel addUser, @HeaderMap Map<String, String> headers);
}
