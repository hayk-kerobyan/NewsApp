package com.hk.newsapp;

import com.hk.newsapp.model.NewsItem;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class NewsRepo {

    @Inject
    public NewsRepo(NetworkManger networkManger) {
        this.networkManger = networkManger;
    }

    private NetworkManger networkManger;
    private NewsLocalRepo localRepo = new NewsLocalRepo();
    private NewsRemoteRepo remoteRepo = new NewsRemoteRepo();

    public Observable<List<NewsItem>> getNews(){
        if(networkManger.isConnected()){
            return remoteRepo.getNews();
        }
        return null;
    }
}
