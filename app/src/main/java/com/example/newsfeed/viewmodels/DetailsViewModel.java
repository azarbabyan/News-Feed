package com.example.newsfeed.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.newsfeed.data.NewsRepository;
import com.example.newsfeed.network.data.Result;

public class DetailsViewModel extends AndroidViewModel {
    private NewsRepository repository;


    public DetailsViewModel(@NonNull Application application) {
        super(application);
        repository = NewsRepository.getInstance(application);
    }

    public LiveData<Result> getResultById(String id) {
        return repository.getResultById(id);
    }
}
