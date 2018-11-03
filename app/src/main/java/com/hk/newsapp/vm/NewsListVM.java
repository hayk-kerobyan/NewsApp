package com.hk.newsapp.vm;

import com.hk.newsapp.enums.RequestResult;
import com.hk.newsapp.model.NewsItem;
import com.hk.newsapp.repositories.NewsRepo;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class NewsListVM extends ViewModel {

    public NewsListVM(NewsRepo newsRepo) {
        this.newsRepo = newsRepo;
        loadNews();
    }

    public static final long DEFAULT_ITEM_ID = -1L;

    private CompositeDisposable disposables = new CompositeDisposable();
    private MutableLiveData<RequestResult> requestResult = new MutableLiveData<>();
    private MutableLiveData<List<NewsItem>> newsItems = new MutableLiveData<>();
    private long lastItemId = DEFAULT_ITEM_ID;
    private NewsRepo newsRepo;

    public void loadNews() {
        requestResult.setValue(RequestResult.IN_PROGRESS);
        disposables.add(newsRepo.getNews()
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<List<NewsItem>>() {
                    @Override
                    public void onNext(List<NewsItem> newsItems) {
                        NewsListVM.this.newsItems.postValue(newsItems);
                        requestResult.postValue(RequestResult.SUCCESS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        requestResult.postValue(RequestResult.FAILURE);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (!disposables.isDisposed())
            disposables.dispose();
    }

    public void updateNewsItem(NewsItem newsItem) {
        disposables.add(Observable.just(newsItem)
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<NewsItem>() {
                    @Override
                    public void onNext(NewsItem newsItem) {
                        newsRepo.updateNewsItem(newsItem);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    public MutableLiveData<List<NewsItem>> getNewsItems() {
        return newsItems;
    }

    public MutableLiveData<RequestResult> getRequestResult() {
        return requestResult;
    }

    public void onResultReceived() {
        requestResult.setValue(RequestResult.NONE);
    }

    public long getLastItemId() {
        return lastItemId;
    }

    public void setLastItemId(long lastItemId) {
        this.lastItemId = lastItemId;
    }
}
