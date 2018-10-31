package com.hk.newsapp.vm.factory;

import com.hk.newsapp.NewsRepo;
import com.hk.newsapp.vm.NewsListVM;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class NewsListFactory extends ViewModelProvider.NewInstanceFactory {

    private NewsRepo newsRepo;

    @Inject
    public NewsListFactory(NewsRepo newsRepo) {
        this.newsRepo = newsRepo;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NewsListVM(newsRepo);
    }
}