package com.hk.newsapp.vm;

import com.hk.newsapp.model.NewsItem;
import com.hk.newsapp.repositories.NewsRepo;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class NewsDetailsVM extends ViewModel {

    @Inject
    public NewsDetailsVM(NewsRepo newsRepo, long id) {
        this.newsRepo = newsRepo;
        this.newsItem = new MutableLiveData<>();
        loadNewsItem(id);
    }

    private MutableLiveData<NewsItem> newsItem;
    private NewsRepo newsRepo;
    private Disposable newsItemDisposable;

    @Override
    protected void onCleared() {
        super.onCleared();
        if(newsItemDisposable!=null && !newsItemDisposable.isDisposed())
            newsItemDisposable.dispose();
    }

    private void loadNewsItem(long id) {
        newsItemDisposable = newsRepo.getByIdWithComponents(id)
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<NewsItem>() {
                    @Override
                    public void onNext(NewsItem newsItem) {
                        NewsDetailsVM.this.newsItem.postValue(newsItem);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public MutableLiveData<NewsItem> getNewsItem() {
        return newsItem;
    }
}
