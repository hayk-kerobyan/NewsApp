package com.hk.newsapp.repositories;

import com.hk.newsapp.Utils.NewsConverter;
import com.hk.newsapp.db.AppDatabase;
import com.hk.newsapp.db.model.NewsWithComponents;
import com.hk.newsapp.model.NewsItem;
import com.hk.newsapp.model.Photo;
import com.hk.newsapp.model.Video;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.core.util.Pair;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

@Singleton
public class NewsLocalRepo {

    @Inject
    public NewsLocalRepo(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    private AppDatabase appDatabase;

    public Observable<List<NewsItem>> getAll() {
        return appDatabase.newsDao().getAll();
    }

    public Observable<NewsItem> getByIdWithComponents(long id) {
        return appDatabase.newsDao().getByIdWithComponents(id)
                .map(NewsConverter::mergeObjects);
    }

    public List<Long> insertNewsItems(List<NewsItem> newsItems) {
        return appDatabase.newsDao().insertNews(newsItems);
    }

    public void insertComponents(Pair<List<Photo>, List<Video>> components) {
        appDatabase.newsDao().insertComponents(components);
    }

    public void insertPhotos(List<Photo> photos) {
        appDatabase.photoDao().insertAll(photos);
    }

    public void insertVideos(List<Video> videos) {
        appDatabase.videoDao().insertAll(videos);
    }

    public void deleteAll() {
        appDatabase.newsDao().deleteAll();
    }
}
