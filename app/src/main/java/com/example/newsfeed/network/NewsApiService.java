package com.example.newsfeed.network;

import com.example.newsfeed.network.models.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiService {
    @GET("search")
    Call<NewsResponse> getNews(@Query("page") Integer startPage);
}
