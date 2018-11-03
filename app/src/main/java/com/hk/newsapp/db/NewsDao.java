package com.hk.newsapp.db;

import com.hk.newsapp.db.model.NewsWithComponents;
import com.hk.newsapp.model.NewsItem;
import com.hk.newsapp.model.Photo;
import com.hk.newsapp.model.Video;

import java.util.List;

import androidx.core.util.Pair;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import io.reactivex.Observable;

@Dao
public abstract class NewsDao {

    public static final String TAG = "LOG_TAG";

    @Update
    public abstract void updateNewsItem(NewsItem newsItem);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract List<Long> insertNews(List<NewsItem> newsItems);

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
