package com.example.newsfeed.data.database;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.newsfeed.network.data.Result;

import java.util.List;


@Dao
public interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long[] insert(List<Result> results);

    @Query("DELETE FROM results")
    void deleteAll();

    @Query("SELECT * FROM results")
    List<Result> getCurrentList();

    @Query("SELECT * FROM results")
    DataSource.Factory<Integer, Result> getPagedNews();

}
