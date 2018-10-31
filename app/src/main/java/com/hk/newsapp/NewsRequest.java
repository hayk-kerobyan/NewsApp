package com.hk.newsapp;

import com.hk.newsapp.model.NewsItem;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface NewsRequest {

    //TODO: Change url
    @GET("news")
    Observable<List<NewsItem>> getNews();
}
