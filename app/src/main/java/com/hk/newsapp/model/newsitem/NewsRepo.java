package com.hk.newsapp.model.newsitem;

import com.hk.newsapp.managers.NetworkManger;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class NewsRepo {

    @Inject
    public NewsRepo(NetworkManger networkManger, NewsRemoteRepo remoteRepo, NewsLocalRepo localRepo) {
        this.networkManger = networkManger;
        this.remoteRepo = remoteRepo;
        this.localRepo = localRepo;
    }

    private NetworkManger networkManger;
    private NewsLocalRepo localRepo;
    private NewsRemoteRepo remoteRepo;

    public Observable<List<NewsItem>> getNews() {
        if (networkManger.isConnected()) {
            return remoteRepo.getNews();
        }
        return localRepo.getNews();
    }

    public Observable<NewsItem> getNewsById(long id) {
        return localRepo.getNewsById(id);
    }
}
