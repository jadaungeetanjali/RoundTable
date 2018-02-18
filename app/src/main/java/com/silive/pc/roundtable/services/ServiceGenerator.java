package com.silive.pc.roundtable.services;

import com.silive.pc.roundtable.config.APIUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by PC on 2/5/2018.
 */

public class ServiceGenerator {


    //building retrofit object
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(APIUrl.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <S> S createService(
            Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
