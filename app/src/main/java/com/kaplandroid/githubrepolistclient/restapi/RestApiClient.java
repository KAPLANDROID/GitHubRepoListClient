package com.kaplandroid.githubrepolistclient.restapi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestApiClient {

    public static final String BASE_URL = "https://api.github.com/";
    private static RestApiInterface apiInterface = null;

    public static RestApiInterface getAPI() {
        if (apiInterface == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiInterface = retrofit.create(RestApiInterface.class);
        }

        return apiInterface;
    }
}