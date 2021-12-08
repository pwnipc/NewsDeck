package com.chalie.newsdeck.services;

import static com.chalie.newsdeck.Constants.NEWSAPI_KEY;

import com.chalie.newsdeck.models.Article;
import com.chalie.newsdeck.models.Everything;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface NewsApi {
    @GET("everything")
    Call<Everything> getArticles(@Query("q") String query, @Query("apiKey") String apiKey);
}
