package com.hk.newsapp.db;

import com.hk.newsapp.model.Photo;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Photo> photos);

    @Query("SELECT * FROM photo")
    Maybe<List<Photo>> getAll();

    @Query("SELECT * FROM photo WHERE newsItemId=:newsItemId")
    Single<List<Photo>> getAllForNewsItem(long newsItemId);
}
