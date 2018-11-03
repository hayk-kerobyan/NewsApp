package com.hk.newsapp.db.model;

import com.hk.newsapp.model.NewsItem;
import com.hk.newsapp.model.Photo;
import com.hk.newsapp.model.Video;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class NewsWithComponents {

    @Embedded
    public NewsItem newsItem;

    @Relation(parentColumn = "id", entityColumn = "newsItemId", entity = Photo.class)
    public List<Photo> photos;

    @Relation(parentColumn = "id", entityColumn = "newsItemId", entity = Video.class)
    public List<Video> videos;
}
