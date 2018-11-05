package com.hk.newsapp;

import com.crashlytics.android.Crashlytics;
import com.hk.newsapp.di.DaggerAppComponent;
import com.squareup.leakcanary.LeakCanary;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import io.fabric.sdk.android.Fabric;

public class App extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        Fabric.with(this, new Crashlytics());
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
