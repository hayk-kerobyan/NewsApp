package com.hk.newsapp.repositories;

import com.hk.newsapp.Utils.NewsConverter;
import com.hk.newsapp.managers.NetworkManger;
import com.hk.newsapp.model.NewsItem;
import com.hk.newsapp.model.Photo;
import com.hk.newsapp.model.Video;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.core.util.Pair;
import io.reactivex.Observable;

@Singleton
public class NewsRepo {

    @Inject
    public NewsRepo(NetworkManger networkManger, NewsRemoteRepo remoteRepo, NewsLocalRepo localRepo) {
        this.networkManger = networkManger;
        this.remoteRepo = remoteRepo;
        this.localRepo = localRepo;
    }

    public static final String TAG = "LOG_TAG";
    private NetworkManger networkManger;
    private NewsLocalRepo localRepo;
    private NewsRemoteRepo remoteRepo;

    public Observable<List<NewsItem>> getNews() {
        if (networkManger.isConnected()) {
            return remoteRepo.getNews().doOnNext(newsItems -> {
                localRepo.deleteAll();
                List<Long> ids = localRepo.insertNewsItems(newsItems);
                Pair<List<Photo>, List<Video>> components = NewsConverter.setIds(newsItems, ids);
                localRepo.insertComponents(components);
            }).flatMap(newsItems -> localRepo.getAll());
        }
        return localRepo.getAll();
    }

    public Observable<NewsItem> getByIdWithComponents(long id) {
        return localRepo.getByIdWithComponents(id);
    }
}
