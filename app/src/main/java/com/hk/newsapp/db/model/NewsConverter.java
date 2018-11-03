package com.hk.newsapp.db.model;

import com.hk.newsapp.model.NewsItem;
import com.hk.newsapp.model.Photo;
import com.hk.newsapp.model.Video;
import com.hk.newsapp.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.core.util.Pair;

public class NewsConverter {

    public static final int ONE_MILLISECOND = 1;
    public static final int ONE_SECOND_IN_MILLIES = ONE_MILLISECOND * 1000;

    public static Pair<List<Photo>, List<Video>> setIds(List<NewsItem> newsItems, List<Long> longs) {
        List<Photo> photos = new ArrayList<>();
        List<Video> videos = new ArrayList<>();
        for (int i = 0; i < newsItems.size(); i++) {
            long id = longs.get(i);
            NewsItem newsItem = newsItems.get(i);
            if (newsItem.getPhotos() != null) {
                for (Photo photo : newsItem.getPhotos()) {
                    photo.setNewsItemId(id);
                    photos.add(photo);
                }
            }
            if (newsItem.getVideos() != null) {
                for (Video video : newsItem.getVideos()) {
                    video.setNewsItemId(id);
                    videos.add(video);
                }
            }
        }
        return new Pair<>(photos, videos);
    }

    public static NewsItem mergeObjects(NewsWithComponents newsWithComponents) {
        NewsItem newsItem = newsWithComponents.newsItem;
        newsItem.setPhotos(newsWithComponents.photos);
        newsItem.setVideos(newsWithComponents.videos);
        return newsItem;
    }

    public static void adaptObjects(List<NewsItem> newsItems) {
        for (NewsItem newsItem : newsItems) {
            newsItem.setDate(newsItem.getDate() * ONE_SECOND_IN_MILLIES);
            newsItem.setBody(TextUtils.htmlToString(newsItem.getBody()));
        }
    }
}
