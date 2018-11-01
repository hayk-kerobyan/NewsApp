package com.hk.newsapp.model.newsitem;

import java.util.List;

import io.reactivex.Observable;

public class NewsLocalRepo {

    public Observable<List<NewsItem>> getNews() {

        return null;
    }

    public Observable<NewsItem> getNewsById(long id) {

        return Observable.just(new NewsItem(id, "Id is " + id));
    }
}
