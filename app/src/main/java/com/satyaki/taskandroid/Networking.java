package com.satyaki.taskandroid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Networking {

    @GET("crew")
    Call<List<Users>> listRepos();
}
