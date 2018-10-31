package com.hk.newsapp.interfaces;

public interface IEventListener {

    void onNewsListInitiated(long id);
    void onNewsItemClicked(long id);

}
