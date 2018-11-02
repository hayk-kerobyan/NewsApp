package com.hk.newsapp.db;

import com.hk.newsapp.model.newsitem.NewsItem;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface NewsDao  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NewsItem... news);

    @Query("SELECT * FROM newsItem")
    List<NewsItem> getAllNews();

    @Query("SELECT * FROM newsItem WHERE id=:id")
    NewsItem getNewsById(long id);

    @Delete
    void delete(NewsItem... news);

    @Query("DELETE FROM newsItem")
    void deleteAll();

}
