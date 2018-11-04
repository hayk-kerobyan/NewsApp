package com.hk.newsapp.di;

import com.hk.newsapp.ui.activities.GalleryActivity;
import com.hk.newsapp.ui.activities.NewsDetailsActivity;
import com.hk.newsapp.ui.activities.NewsListActivity;
import com.hk.newsapp.ui.fragments.GalleryFragment;
import com.hk.newsapp.ui.fragments.NewsDetailsFrag;
import com.hk.newsapp.ui.fragments.PhotoPreviewFrag;

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

    @ContributesAndroidInjector
    abstract GalleryActivity galleryActivity();

    @ContributesAndroidInjector
    abstract GalleryFragment galleryFragment();

    @ContributesAndroidInjector
    abstract PhotoPreviewFrag photoPreviewFrag();

}