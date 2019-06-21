package com.example.newsfeed.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.newsfeed.data.NewsRepository;
import com.example.newsfeed.network.data.Result;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private NewsRepository repository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = NewsRepository.getInstance(application);
    }

    public LiveData<List<Result>> getNews(Integer startPage) {
        return repository.getNews(startPage);
    }
}
