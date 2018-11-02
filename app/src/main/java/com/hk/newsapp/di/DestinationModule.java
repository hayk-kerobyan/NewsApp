package com.hk.newsapp.di;

import com.hk.newsapp.ui.activities.NewsDetailsActivity;
import com.hk.newsapp.ui.activities.NewsListActivity;
import com.hk.newsapp.ui.fragments.NewsDetailsFrag;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DestinationModule {

    @ContributesAndroidInjector
    abstract NewsListActivity newsListActivity();

    @ContributesAndroidInjector(modules = AppModule.class)
    abstract NewsDetailsActivity newsDetailsActivity();

    @ContributesAndroidInjector
    abstract NewsDetailsFrag newsDetailsFrag();

}