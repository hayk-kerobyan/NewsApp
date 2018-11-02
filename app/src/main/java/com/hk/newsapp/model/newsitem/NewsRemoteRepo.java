package com.hk.newsapp.model.newsitem;

import com.hk.newsapp.model.NewsResponse;
import com.hk.newsapp.network.RetroService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class NewsRemoteRepo {

    @Inject
    public NewsRemoteRepo(RetroService retroService) {
        this.retroService = retroService;
    }

    RetroService retroService;

    public Observable<List<NewsItem>> getNews() {
        return retroService.getNews().filter(NewsResponse::getSuccess)
                .flatMap(newsResponse -> Observable.just(newsResponse.getData()));


    }

    /*    public Observable<List<NewsItem>> getNews() {
        return retroService.getNews()
                .filter(NewsResponse::getSuccess)
                .flatMap(newsResponse -> Observable.just(newsResponse.getData()));
    }*/
}
