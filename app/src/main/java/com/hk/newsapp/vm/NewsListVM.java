package com.hk.newsapp.vm;

import com.hk.newsapp.enums.RequestResult;
import com.hk.newsapp.model.NewsItem;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewsListVM extends ViewModel {

    public NewsListVM() {
        requestResult = new MutableLiveData<>();
        this.newsItems = new MutableLiveData<>();
        List<NewsItem> newsItems = new ArrayList<>();
        newsItems.add(new NewsItem(1L, "das"));
        newsItems.add(new NewsItem(2L, "asd"));
        newsItems.add(new NewsItem(3L, "dsa"));
        newsItems.add(new NewsItem(4L, "sad"));
        this.newsItems.setValue(newsItems);
    }

    public static final long DEFAULT_ITEM_ID = -1L;

    private MutableLiveData<RequestResult> requestResult;
    private MutableLiveData<List<NewsItem>> newsItems;
    private long lastItemId = DEFAULT_ITEM_ID;


    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public MutableLiveData<List<NewsItem>> getNewsItems() {
        return newsItems;
    }

    public MutableLiveData<RequestResult> getRequestResult() {
        return requestResult;
    }

    public void onResultReceived(){
        requestResult.setValue(RequestResult.NONE);
    }

    public long getLastItemId() {
        return lastItemId;
    }

    public void setLastItemId(long lastItemId) {
        this.lastItemId = lastItemId;
    }
}
