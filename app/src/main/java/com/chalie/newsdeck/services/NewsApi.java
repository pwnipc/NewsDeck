package com.chalie.newsdeck.services;

import com.chalie.newsdeck.models.Everything;
import com.chalie.newsdeck.models.Source;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("everything?q={query}")
    Call<Everything> getArticles(
            @Query("query") String query
    );
}
