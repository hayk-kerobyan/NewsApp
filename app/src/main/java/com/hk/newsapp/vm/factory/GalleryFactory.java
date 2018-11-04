package com.hk.newsapp.vm.factory;

import com.hk.newsapp.enums.ContentType;
import com.hk.newsapp.repositories.NewsLocalRepo;
import com.hk.newsapp.vm.GalleryVM;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import static com.hk.newsapp.utils.Constants.DEFAULT_ITEM_ID;


@Singleton
public class GalleryFactory extends ViewModelProvider.NewInstanceFactory {

    private NewsLocalRepo localRepo;
    private ContentType contentType;
    private long newsItemId;

    @Inject
    public GalleryFactory(NewsLocalRepo localRepo) {
        this.localRepo = localRepo;
        this.newsItemId = DEFAULT_ITEM_ID;

    }

    public GalleryFactory withId(ContentType contentType, long newsIitemId) {
        this.contentType = contentType;
        this.newsItemId = newsIitemId;
        return this;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (newsItemId == DEFAULT_ITEM_ID || contentType == null)
            throw new IllegalArgumentException("Id or content type are not set before VM creation");
        return (T) new GalleryVM(localRepo, contentType, newsItemId);
    }
}