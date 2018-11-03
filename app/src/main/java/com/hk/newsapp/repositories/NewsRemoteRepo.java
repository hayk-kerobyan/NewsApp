package com.hk.newsapp.repositories;

import com.hk.newsapp.model.NewsResponse;
import com.hk.newsapp.model.NewsItem;
import com.hk.newsapp.network.RetroService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
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
        return retroService.getNews()
                .filter(new Predicate<NewsResponse>() {
                    @Override
                    public boolean test(NewsResponse newsResponse) throws Exception {
                        return newsResponse.getSuccess();
                    }
                })
                .map(new Function<NewsResponse, List<NewsItem>>() {
                    @Override
                    public List<NewsItem> apply(NewsResponse newsResponse) throws Exception {
                        return newsResponse.getData();
                    }
                });
    }
}
