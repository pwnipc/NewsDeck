package com.chalie.newsdeck.services;

import static com.chalie.newsdeck.Constants.BASE_URL;
import static com.chalie.newsdeck.Constants.NEWSAPI_KEY;

import com.chalie.newsdeck.services.NewsApi;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsApiClient {
    public static Retrofit retrofit = null;

    public static NewsApi getClient(){
        if(retrofit == null){
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor(){
                @Override
                public Response intercept(Chain chain) throws IOException{
                    Request newRequest = chain
                            .request()
                            .newBuilder()
                            .addHeader("apiKey",NEWSAPI_KEY)
                            .build();
                    return chain.proceed(newRequest);
                }
            })
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(NewsApi.class);
    }
}
