package com.hk.newsapp.vm.factory;

import com.hk.newsapp.repositories.NewsRepo;
import com.hk.newsapp.vm.NewsDetailsVM;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import static com.hk.newsapp.utils.Constants.DEFAULT_ITEM_ID;


@Singleton
public class NewsDetailsFactory extends ViewModelProvider.NewInstanceFactory {

    private NewsRepo newsRepo;
    private long id;

    @Inject
    public NewsDetailsFactory(NewsRepo newsRepo) {
        this.newsRepo = newsRepo;
        this.id = DEFAULT_ITEM_ID;
    }

    public NewsDetailsFactory withId(long id) {
        this.id = id;
        return this;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (id == DEFAULT_ITEM_ID)
            throw new IllegalArgumentException("Id is not set before VM creation");
        return (T) new NewsDetailsVM(newsRepo, id);
    }
}