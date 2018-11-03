package com.hk.newsapp.Utils;

import com.hk.newsapp.db.model.NewsWithComponents;
import com.hk.newsapp.model.NewsItem;
import com.hk.newsapp.model.Photo;
import com.hk.newsapp.model.Video;

import java.util.ArrayList;
import java.util.List;

import androidx.core.util.Pair;

public class NewsConverter {

    public static Pair<List<Photo>, List<Video>> setIds(List<NewsItem> newsItems, List<Long> longs) {
        List<Photo> photos = new ArrayList<>();
        List<Video> videos = new ArrayList<>();
        for (int i = 0; i < newsItems.size(); i++) {
            long id = longs.get(i);
            NewsItem newsItem = newsItems.get(i);
            if(newsItem.getPhotos()!=null) {
                for (Photo photo : newsItem.getPhotos()) {
                    photo.setNewsItemId(id);
                    photos.add(photo);
                }
            }
            if(newsItem.getVideos()!=null) {
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

}
