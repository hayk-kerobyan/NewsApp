package com.hk.newsapp.network;

import com.hk.newsapp.model.newsitem.NewsItem;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RetroService {

    //TODO: Change url
    @GET("news")
    Observable<List<NewsItem>> getNews();
}
