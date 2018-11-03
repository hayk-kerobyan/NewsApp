package com.hk.newsapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hk.newsapp.R;
import com.hk.newsapp.model.NewsItem;
import com.hk.newsapp.vm.NewsDetailsVM;
import com.hk.newsapp.vm.factory.NewsDetailsFactory;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class NewsDetailsFrag extends BaseFragment {

    public static NewsDetailsFrag newInstance(long id) {

        Bundle args = new Bundle();
        args.putLong(NEWS_ITEM_ID_KEY, id);
        NewsDetailsFrag fragment = new NewsDetailsFrag();
        fragment.setArguments(args);
        return fragment;
    }

    public static final String NEWS_ITEM_ID_KEY = "NEWS_ITEM_ID_KEY";
    public static final String NEWS_DETAILS_TAG = "NEWS_DETAILS_TAG";

    @Inject
    NewsDetailsFactory newsDetailsFactory;

    private NewsDetailsVM newsDetailsVM;

    private TextView textView;

    private NewsItem newsItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_news_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setUpViewModel();
    }

    @Override
    public void onStart() {
        super.onStart();
        subscribeObservers();
    }

    @Override
    public void onStop() {
        super.onStop();
        unsubscribeObservers();
    }

    private Observer<NewsItem> newsDetailsObserver = newsItem -> {
        if (newsItem != null) {
            NewsDetailsFrag.this.newsItem = newsItem;
            updateUI();
        }
    };

    private void initViews(View view) {
        textView = view.findViewById(R.id.textView);
    }

    private void setUpViewModel() {
        newsDetailsVM = ViewModelProviders.of(this,
                newsDetailsFactory.withId(getArguments().getLong(NEWS_ITEM_ID_KEY)))
                .get(NewsDetailsVM.class);
    }

    private void updateUI() {
        textView.setText(newsItem.getTitle());
    }

    private void subscribeObservers() {
        newsDetailsVM.getNewsItem().observe(this, newsDetailsObserver);
    }

    private void unsubscribeObservers() {
        newsDetailsVM.getNewsItem().removeObserver(newsDetailsObserver);
    }


}
