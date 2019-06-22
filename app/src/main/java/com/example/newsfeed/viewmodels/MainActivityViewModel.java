package com.example.newsfeed.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.newsfeed.data.NewsRepository;
import com.example.newsfeed.data.database.PinedNews;
import com.example.newsfeed.network.data.Result;
import com.example.newsfeed.network.models.NewsResponse;
import com.example.newsfeed.network.models.Resource;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private NewsRepository repository;
    private final LiveData<PagedList<Result>> newsList;
    private final LiveData<PagedList<PinedNews>> pagedListLiveData;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = NewsRepository.getInstance(application);
        newsList = new LivePagedListBuilder<>(repository.getNewsDao().getPagedNews(), 20)
                .build();
        pagedListLiveData = new LivePagedListBuilder<>(repository.getNewsDao().getPinnedPagedNews(),10)
                .build();
    }


    public MutableLiveData<Resource<NewsResponse>> getNews(Integer startPage) {
        return repository.getNews(startPage);
    }

    public LiveData<PagedList<Result>> getNewsList() {
        return newsList;
    }

    public LiveData<PagedList<PinedNews>> getPinnedNews() {
        return pagedListLiveData;
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
