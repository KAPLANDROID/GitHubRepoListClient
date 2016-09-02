package com.kaplandroid.githubrepolistclient.restapi;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by KAPLANDROID on 2.09.2016.
 */
public class RestApiClient {

    private static final String BASE_URL = "https://api.github.com/";
    private static RestApiInterface apiInterface = null;

    public static RestApiInterface getAPI() {
        if (apiInterface == null) {


            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(logInterceptor)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request originalRequest = chain.request();

                            Request newRequest = originalRequest.newBuilder()
                                    .addHeader("Content-Type", "application/json;charset=UTF-8")
                                    .cacheControl(CacheControl.FORCE_NETWORK)
                                    .build();

                            return chain.proceed(newRequest);
                        }
                    }).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiInterface = retrofit.create(RestApiInterface.class);
        }

        return apiInterface;
    }
}