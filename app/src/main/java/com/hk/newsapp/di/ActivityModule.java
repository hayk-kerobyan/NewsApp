package com.hk.newsapp.di;

import com.hk.newsapp.ui.activities.BaseActivity;
import com.hk.newsapp.ui.activities.NewsDetailsActivity;
import com.hk.newsapp.ui.activities.NewsListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector(modules = AppModule.class)
    abstract BaseActivity baseActivity();

    @ContributesAndroidInjector
    abstract NewsListActivity newsListActivity();

    @ContributesAndroidInjector(modules = AppModule.class)
    abstract NewsDetailsActivity newsDetailsActivity();
}