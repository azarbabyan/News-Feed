package com.example.newsfeed.data;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.newsfeed.NewsFeedApplication;
import com.example.newsfeed.data.database.NewsDao;
import com.example.newsfeed.data.database.NewsDatabase;
import com.example.newsfeed.network.data.Result;
import com.example.newsfeed.network.models.NewsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private NewsDao newsDao;
    private Application application;
    private static NewsRepository instance;

    private NewsRepository(Application application){
        NewsDatabase database = NewsDatabase.getDatabase(application);
        newsDao = database.newsDao();
        this.application = application;
    }

    public static NewsRepository getInstance(Application application){
        if (instance == null){
            synchronized (NewsRepository.class){
                if (instance == null){
                    instance = new NewsRepository(application);
                }
            }
        }
        return instance;
    }

    public LiveData<List<Result>> getNews(Integer startPage){
        NewsFeedApplication.getApiService().getNews(startPage,100).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.body() !=null){
                    new Thread(() -> {
                        com.example.newsfeed.network.models.Response response1 = response.body().getResponse();
                        if (response1!=null){
                            List<Result> results = response1.getResults();
                            if (results!=null && results.size()>0){
                                for (Result result:results){
                                    newsDao.insert(result);
                                }
                            }
                        }
                    }).start();


                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Toast.makeText(application, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
        return newsDao.getAllNews();
    }

    public NewsDao getNewsDao() {
        return newsDao;
    }
}
