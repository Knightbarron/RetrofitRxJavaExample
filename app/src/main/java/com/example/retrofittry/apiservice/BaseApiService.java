package com.example.retrofittry.apiservice;

import com.example.retrofittry.model.ResponseRepos;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BaseApiService {

    @GET("users/{username}/repos")
    Observable<List<ResponseRepos>> requestRepos(@Path("username") String username);

}
