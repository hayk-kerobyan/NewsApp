package com.hk.newsapp.vm;

import com.hk.newsapp.enums.ContentType;
import com.hk.newsapp.model.Photo;
import com.hk.newsapp.model.Video;
import com.hk.newsapp.repositories.NewsLocalRepo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class GalleryVM extends ViewModel {

    public GalleryVM(NewsLocalRepo localRepo, @NonNull ContentType contentType, long newsItemId) {
        this.localRepo = localRepo;
        initValues(contentType);
        loadContent(contentType, newsItemId);
    }

    private MutableLiveData<List<Photo>> photos;
    private MutableLiveData<List<Video>> videos;
    private NewsLocalRepo localRepo;
    private Disposable disposable;

    @Override
    protected void onCleared() {
        super.onCleared();
        if(disposable!=null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }

    private void loadContent(ContentType contentType, long newsItemId) {
        switch (contentType) {
            case PHOTO:
                disposable = localRepo.getPhotosForNewsItem(newsItemId)
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableSingleObserver<List<Photo>>() {
                            @Override
                            public void onSuccess(List<Photo> photos) {
                                GalleryVM.this.photos.postValue(photos);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });
                break;
            case VIDEO:
                disposable = localRepo.getVideosForNewsItem(newsItemId)
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableSingleObserver<List<Video>>() {
                            @Override
                            public void onSuccess(List<Video> videos) {
                                GalleryVM.this.videos.postValue(videos);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });
        }
    }

    private void initValues(ContentType contentType) {
        switch (contentType) {
            case PHOTO:
                photos = new MutableLiveData<>();
                break;
            case VIDEO:
                videos = new MutableLiveData<>();
                break;
            default:
                throw new IllegalStateException("Unhandled content type");
        }
    }

    public MutableLiveData<List<Photo>> getPhotos() {
        return photos;
    }

    public MutableLiveData<List<Video>> getVideos() {
        return videos;
    }
}
