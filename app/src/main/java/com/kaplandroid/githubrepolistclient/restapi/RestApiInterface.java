package com.kaplandroid.githubrepolistclient.restapi;

import com.kaplandroid.githubrepolistclient.restapi.model.response.RepoInfo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by KAPLANDROID on 2.09.2016.
 */
public interface RestApiInterface {

    @GET("users/{user}/repos")
    Call<ArrayList<RepoInfo>> getUserRepoList(@Path("user") String user,
                                              @Query("type") String type,
                                              @Query("page") int page,
                                              @Query("per_page") int perPage);


}
