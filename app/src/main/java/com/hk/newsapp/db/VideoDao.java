package com.hk.newsapp.db;

import com.hk.newsapp.model.Video;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface VideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Video> videos);

    @Query("SELECT * FROM video")
    Maybe<List<Video>> getAll();

    @Query("SELECT * FROM video WHERE newsItemId=:newsItemId")
    Single<List<Video>> getAllForNewsItem(long newsItemId);
}
