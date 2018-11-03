package com.hk.newsapp.db;


import com.hk.newsapp.model.Photo;
import com.hk.newsapp.model.Video;
import com.hk.newsapp.model.NewsItem;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import static com.hk.newsapp.db.AppDatabase.VERSION;

@Database(entities = {NewsItem.class, Photo.class, Video.class}, version = VERSION)
public abstract class AppDatabase extends RoomDatabase {

    public static final int VERSION = 1;

    public abstract NewsDao newsDao();
    public abstract PhotoDao photoDao();
    public abstract VideoDao videoDao();
}
