package com.hk.newsapp.repositories;

import com.hk.newsapp.db.model.NewsConverter;
import com.hk.newsapp.db.AppDatabase;
import com.hk.newsapp.model.NewsItem;
import com.hk.newsapp.model.Photo;
import com.hk.newsapp.model.Video;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.core.util.Pair;
import io.reactivex.Observable;
import io.reactivex.Single;

@Singleton
public class NewsLocalRepo {

    @Inject
    NewsLocalRepo(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    private AppDatabase appDatabase;

    //Updates
    void updateNewsItem(NewsItem newsItem) {
        appDatabase.newsDao().updateNewsItem(newsItem);
    }

    //Inserts
    List<Long> insertNewsItems(List<NewsItem> newsItems) {
        return appDatabase.newsDao().insertNews(newsItems);
    }

    void insertComponents(Pair<List<Photo>, List<Video>> components) {
        appDatabase.newsDao().insertComponents(components);
    }

    //Queries
    Observable<NewsItem> getByIdWithComponents(long id) {
        return appDatabase.newsDao().getByIdWithComponents(id)
                .map(NewsConverter::mergeObjects);
    }

    public Single<List<Photo>> getPhotosForNewsItem(long newsItemId) {
        return appDatabase.photoDao().getAllForNewsItem(newsItemId);
    }

    public Single<List<Video>> getVideosForNewsItem(long newsItemId) {
        return appDatabase.videoDao().getAllForNewsItem(newsItemId);
    }

    public Observable<List<NewsItem>> getAll() {
        return appDatabase.newsDao().getAll();
    }

    //Deletes
    void deleteAll() {
        appDatabase.newsDao().deleteAll();
    }
}
