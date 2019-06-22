package com.example.newsfeed.network.models;

import androidx.lifecycle.MutableLiveData;
import androidx.annotation.MainThread;

public abstract class NetworkBoundResource<T> {

    private final MutableLiveData<Resource<T>> result = new MutableLiveData<>();

    @MainThread
    protected NetworkBoundResource(boolean setLoading) {
        if (setLoading) {
            result.setValue(Resource.loading(null));
        }
        createCall();
    }

    @MainThread
    protected abstract void createCall();

    public void setResultValue(Resource<T> value) {
        result.setValue(value);
    }

    public final MutableLiveData<Resource<T>> getAsMutableLiveData() {
        return result;
    }

}