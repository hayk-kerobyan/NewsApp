package com.hk.newsapp.di;

import com.google.gson.Gson;
import com.hk.newsapp.network.RetroService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
class NetworkModule {

    @Singleton
    @Provides
    RetroService providesRetroService(Retrofit retrofit) {
        return retrofit.create(RetroService.class);
    }

    @Singleton
    @Provides
    Retrofit providesRetrofit(OkHttpClient okHttpClient, GsonConverterFactory gsonFactory) {
        return new Retrofit.Builder()
                .baseUrl("http://develandoo.getsandbox.com/")
                .client(okHttpClient)
                .addConverterFactory(gsonFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    OkHttpClient providesOkHttpClient() {
        return new OkHttpClient();
    }

    @Singleton
    @Provides
    GsonConverterFactory providesConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Singleton
    @Provides
    Gson providesGson() {
        return new Gson();
    }
}
