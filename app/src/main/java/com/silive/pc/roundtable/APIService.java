package com.silive.pc.roundtable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by PC on 2/2/2018.
 */

public interface APIService {

    @Headers({"Content-Type: application/json",
    "Authorization: Basic Z2VldGFuamFsaTpnZWV0YW5qYWxp"})
    @POST("account/register")
    Call<ResponseBody> createUser(@Body User user);
}
