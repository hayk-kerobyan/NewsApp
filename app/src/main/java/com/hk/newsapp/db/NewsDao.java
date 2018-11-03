package com.hk.newsapp.db;

import android.util.Log;

import com.hk.newsapp.db.model.NewsWithComponents;
import com.hk.newsapp.model.NewsItem;
import com.hk.newsapp.model.Photo;
import com.hk.newsapp.model.Video;

import java.util.ArrayList;
import java.util.List;

import androidx.core.util.Pair;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;

@Dao
public abstract class NewsDao {

    public static final String TAG = "LOG_TAG";

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract List<Long> insertNews(List<NewsItem> newsItem);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertPhotos(List<Photo> photos);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertVideos(List<Video> videos);

    @Query("SELECT * FROM newsItem")
    public abstract Observable<List<NewsItem>> getAll();

    @Query("SELECT * FROM newsItem WHERE id=:id")
    public abstract Observable<NewsItem> getById(long id);

    @Query("SELECT * FROM newsItem WHERE id=:id")
    public abstract Observable<NewsWithComponents> getByIdWithComponents(long id);

    @Query("DELETE FROM newsItem")
    public abstract void deleteAll();

    @Transaction
    public void insertComponents(Pair<List<Photo>, List<Video>> components){
        insertPhotos(components.first);
        insertVideos(components.second);
    }
}
