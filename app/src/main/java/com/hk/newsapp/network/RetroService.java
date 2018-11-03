package com.hk.newsapp.network;

import com.hk.newsapp.model.NewsResponse;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RetroService {

    @GET("feed")
    Observable<NewsResponse> getNews();

}
