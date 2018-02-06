package com.silive.pc.roundtable;

import android.util.Log;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by PC on 2/2/2018.
 */

public interface APIService {


    // the signup call
    @Headers("Content-Type: application/json")
    @POST("account/register")
    Call<ResponseBody> createUser(@Body User user);

    // the signing call
    @Headers("Content-Type: application/json")
    @POST("account/login")
    Call<User> userLogin(@Body User user);
}
