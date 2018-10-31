package com.hk.newsapp;

import android.app.Application;

import com.hk.newsapp.model.NewsItem;

import java.util.List;

import io.reactivex.Observable;

public class NewsRepo {

    private Application application;
    private NetworkManger networkManger = new NetworkManger(application);
    private NewsLocalRepo localRepo = new NewsLocalRepo();
    private NewsRemoteRepo remoteRepo = new NewsRemoteRepo();

    public Observable<List<NewsItem>> getNews(){
//        if(networkManger.isConnected()){
            return remoteRepo.getNews();
//        }
//        return localRepo.getNews();
    }
}
