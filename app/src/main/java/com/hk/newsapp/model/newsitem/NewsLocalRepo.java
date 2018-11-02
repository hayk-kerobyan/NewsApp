package com.hk.newsapp.model.newsitem;

import com.hk.newsapp.db.AppDatabase;
import com.hk.newsapp.model.Photo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class NewsLocalRepo {

    @Inject
    public NewsLocalRepo(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    private AppDatabase appDatabase;

    public Observable<List<NewsItem>> getNews() {
        return Observable.just(new ArrayList<>());
    }

    public Observable<NewsItem> getNewsById(long id) {
        return Observable.just(null);
    }
}
