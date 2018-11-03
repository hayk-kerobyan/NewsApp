package com.hk.newsapp.repositories;

import com.hk.newsapp.model.NewsItem;
import com.hk.newsapp.model.NewsResponse;
import com.hk.newsapp.network.RetroService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class NewsRemoteRepo {

    @Inject
    NewsRemoteRepo(RetroService retroService) {
        this.retroService = retroService;
    }

    private RetroService retroService;

    Observable<List<NewsItem>> getNews() {
        return retroService.getNews()
                .filter(NewsResponse::getSuccess)
                .map(NewsResponse::getData);
    }
}
