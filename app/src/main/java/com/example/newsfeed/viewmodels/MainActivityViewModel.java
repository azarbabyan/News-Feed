package com.example.newsfeed.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.newsfeed.data.NewsRepository;
import com.example.newsfeed.network.data.Result;
import com.example.newsfeed.network.models.NewsResponse;
import com.example.newsfeed.network.models.Resource;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private NewsRepository repository;
    private final LiveData<PagedList<Result>> newsList;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = NewsRepository.getInstance(application);
        newsList = new LivePagedListBuilder<>(repository.getNewsDao().getPagedNews(), 10)
                .build();
    }


    public MutableLiveData<Resource<NewsResponse>> getNews(Integer startPage) {
        return repository.getNews(startPage);
    }

    public LiveData<PagedList<Result>> getNewsList() {
        return newsList;
    }

    public int currentDBListSize() {
        return repository.currentDBListSize();
    }

    public boolean insertResultsToDb(List<Result> results) {
        return repository.insertResultsToDb(results);
    }

    public void deleteDb() {
        repository.deleteDb();
    }
}
