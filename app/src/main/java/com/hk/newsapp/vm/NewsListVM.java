package com.hk.newsapp.vm;

import com.hk.newsapp.enums.RequestResult;
import com.hk.newsapp.model.NewsItem;
import com.hk.newsapp.repositories.NewsRepo;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.hk.newsapp.utils.Constants.DEFAULT_ITEM_ID;

public class NewsListVM extends ViewModel {

    public NewsListVM(NewsRepo newsRepo) {
        this.newsRepo = newsRepo;
        loadNews();
    }

    private Disposable disposable;
    private MutableLiveData<RequestResult> requestResult = new MutableLiveData<>();
    private MutableLiveData<List<NewsItem>> newsItems = new MutableLiveData<>();
    private long lastItemId = DEFAULT_ITEM_ID;
    private NewsRepo newsRepo;

    public void loadNews() {
        if(disposable!=null && !disposable.isDisposed()){
            return;
        }
        requestResult.setValue(RequestResult.IN_PROGRESS);
        disposable = newsRepo.getNews()
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<List<NewsItem>>() {
                    @Override
                    public void onNext(List<NewsItem> newsItems) {
                        disposable.dispose();
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
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
    }

    public void updateNewsItem(NewsItem newsItem) {
        Single.just(newsItem).subscribeOn(Schedulers.io()).subscribe(new SingleObserver<NewsItem>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(NewsItem newsItem) {
                newsRepo.updateNewsItem(newsItem);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
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
