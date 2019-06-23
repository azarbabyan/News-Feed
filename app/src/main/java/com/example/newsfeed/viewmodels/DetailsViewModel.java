package com.example.newsfeed.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.newsfeed.data.NewsRepository;
import com.example.newsfeed.data.database.PinedNews;
import com.example.newsfeed.network.data.Result;

public class DetailsViewModel extends AndroidViewModel {
    private NewsRepository repository;
    private String resultId;
    private boolean isPined;


    public DetailsViewModel(@NonNull Application application) {
        super(application);
        repository = NewsRepository.getInstance(application);
    }

    public LiveData<Result> getResultById(String id) {
        return repository.getResultById(id);
    }

    public boolean insertPinnedNews(PinedNews pinedNews){
       return repository.insertPinedNews(pinedNews);
    }

    public boolean isPined(String id){
        return repository.isPined(id);
    }

    public void  deletePinnedNews(PinedNews pinedNews){
        repository.deletePinedNews(pinedNews);
    }

    public String getResultId() {
        return resultId;
    }

    public boolean isPined() {
        return isPined;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public void setPined(boolean pined) {
        isPined = pined;
    }
}
