package com.hk.newsapp.di;

import android.content.Context;

import com.hk.newsapp.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class AppModule {

    @Provides
    @Singleton
    Context providesContext(App application) {
        return application.getApplicationContext();
    }
}
