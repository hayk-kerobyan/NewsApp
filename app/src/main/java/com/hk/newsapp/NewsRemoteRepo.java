package com.hk.newsapp;

import com.hk.newsapp.model.NewsItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class NewsRemoteRepo {

    public Observable<List<NewsItem>> getNews(){
        List<NewsItem> newsItems = new ArrayList<>();
        newsItems.add(new NewsItem(1L, "das"));
        newsItems.add(new NewsItem(2L, "asd"));
        newsItems.add(new NewsItem(3L, "dsa"));
        newsItems.add(new NewsItem(4L, "sad"));
        return Observable.just(newsItems).delay(2000, TimeUnit.MILLISECONDS);
    }
}
