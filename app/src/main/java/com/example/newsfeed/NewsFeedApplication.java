package com.example.newsfeed;

import android.app.Application;

import com.example.newsfeed.network.HeaderInterceptor;
import com.example.newsfeed.network.NewsApiService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.newsfeed.utils.AppConstants.BASE_URL;

public class NewsFeedApplication extends Application {

    private static NewsApiService apiService;
    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder();
        httpClient.addInterceptor(new HeaderInterceptor());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(NewsApiService.class);
    }

    public static NewsApiService getApiService() {
        return apiService;
    }
}
