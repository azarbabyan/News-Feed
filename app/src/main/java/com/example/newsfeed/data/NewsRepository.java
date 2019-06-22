package com.example.newsfeed.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.newsfeed.NewsFeedApplication;
import com.example.newsfeed.data.database.NewsDao;
import com.example.newsfeed.data.database.NewsDatabase;
import com.example.newsfeed.network.data.Result;
import com.example.newsfeed.network.models.NetworkBoundResource;
import com.example.newsfeed.network.models.NewsResponse;
import com.example.newsfeed.network.models.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private NewsDao newsDao;
    private Application application;
    private static NewsRepository instance;

    private NewsRepository(Application application) {
        NewsDatabase database = NewsDatabase.getDatabase(application);
        newsDao = database.newsDao();
        this.application = application;
    }

    public static NewsRepository getInstance(Application application) {
        if (instance == null) {
            synchronized (NewsRepository.class) {
                if (instance == null) {
                    instance = new NewsRepository(application);
                }
            }
        }
        return instance;
    }

    public void deleteDb() {
        new Thread(() -> {
            newsDao.deleteAll();
        }).start();
    }

    public boolean insertResultsToDb(List<Result> results) {
        List<Long> list = new ArrayList<>();
        Thread t = new Thread(() -> {
            if (results != null && results.size() > 0) {
                Long[] insert = newsDao.insert(results);
                list.addAll(Arrays.asList(insert));
            }
        });
        t.start();
        try {
            t.join();
            return list.size() > 0;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public MutableLiveData<Resource<NewsResponse>> getNews(Integer startPage) {
        return new NetworkBoundResource<NewsResponse>(true) {
            @Override
            protected void createCall() {
                NewsFeedApplication.getApiService().getNews(startPage, 100)
                        .enqueue(new Callback<NewsResponse>() {
                            @Override
                            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                                if (response.code() == 200 || response.code() == 201) {
                                    setResultValue(Resource.success(Objects.requireNonNull(response.body())));
                                } else {
                                    setResultValue(Resource.error(response.message(), null));
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {
                                setResultValue(Resource.error("Failed to fetch account", null));
                            }
                        });
            }
        }.getAsMutableLiveData();
    }

    public NewsDao getNewsDao() {
        return newsDao;
    }

    public int currentDBListSize() {
        List<Integer> count = new ArrayList<>();
        Thread t = new Thread(() -> {
            count.add(newsDao.getCurrentList().size());
        });
        t.start();
        try {
            t.join();
            return count.get(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
