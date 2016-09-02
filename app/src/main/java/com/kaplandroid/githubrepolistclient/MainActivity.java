package com.kaplandroid.githubrepolistclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.kaplandroid.githubrepolistclient.restapi.RestApiClient;
import com.kaplandroid.githubrepolistclient.restapi.model.response.RepoInfo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by KAPLANDROID on 2.09.2016.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Call<ArrayList<RepoInfo>> call = RestApiClient.getAPI().getUserRepoList("kaplandroid", "owner", "1", "57");
        call.enqueue(new Callback<ArrayList<RepoInfo>>() {

            @Override
            public void onResponse(Call<ArrayList<RepoInfo>> call, Response<ArrayList<RepoInfo>> response) {

                ArrayList<RepoInfo> list = response.body();

                for (RepoInfo info : list) {
                    Log.e("res", info.getFull_name());
                }


            }

            @Override
            public void onFailure(Call<ArrayList<RepoInfo>> call, Throwable t) {

            }
        });


    }
}
