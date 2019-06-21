package com.example.newsfeed.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.newsfeed.network.data.Result;

import java.util.List;

@Dao
public interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Result result);

    @Query("DELETE FROM results")
    void deleteAll();

    @Query("SELECT * FROM results")
    LiveData<List<Result>> getAllNews();


}
