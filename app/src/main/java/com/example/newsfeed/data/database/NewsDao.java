package com.example.newsfeed.data.database;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
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
    void deleteAllResults();

    @Query("SELECT * FROM results")
    List<Result> getCurrentList();

    @Query("SELECT * FROM results")
    DataSource.Factory<Integer, Result> getPagedNews();

    @Query("SELECT * from results WHERE id =:id")
    LiveData<Result> getResultById(String id);

    @Query("SELECT * FROM pinnednews")
    LiveData<PinedNews> getAllPinnedNews();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertPinedNews(PinedNews pinedNews);

    @Query("SELECT * FROM pinnednews")
    DataSource.Factory<Integer, PinedNews> getPinnedPagedNews();

    @Query("SELECT * FROM pinnednews WHERE id =:id")
    PinedNews getPinedNewsById(String id);

    @Delete
    void deletePinedNews(PinedNews pinedNews);

}
