package com.example.newsfeed.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


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
