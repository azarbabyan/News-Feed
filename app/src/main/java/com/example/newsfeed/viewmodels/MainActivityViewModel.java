package com.example.newsfeed.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.newsfeed.data.NewsRepository;
import com.example.newsfeed.network.data.Result;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private NewsRepository repository;
    private final LiveData<PagedList<Result>> newsList;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = NewsRepository.getInstance(application);
        newsList = new LivePagedListBuilder<>(repository.getNewsDao().getPaggedNews(),10)
                .build();
    }

    public LiveData<List<Result>> getNews(Integer startPage) {
        return repository.getNews(startPage);
    }

    public LiveData<PagedList<Result>> getNewsList() {
        return newsList;
    }
}
