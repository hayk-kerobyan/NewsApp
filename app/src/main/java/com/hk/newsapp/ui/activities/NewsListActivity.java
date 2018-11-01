package com.hk.newsapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hk.newsapp.R;
import com.hk.newsapp.enums.RequestResult;
import com.hk.newsapp.model.newsitem.NewsItem;
import com.hk.newsapp.ui.adapters.NewsAdapter;
import com.hk.newsapp.ui.fragments.NewsDetailsFrag;
import com.hk.newsapp.vm.NewsListVM;
import com.hk.newsapp.vm.factory.NewsListFactory;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import static com.hk.newsapp.ui.fragments.NewsDetailsFrag.NEWS_DETAILS_TAG;
import static com.hk.newsapp.ui.fragments.NewsDetailsFrag.NEWS_ITEM_ID_KEY;
import static com.hk.newsapp.vm.NewsListVM.DEFAULT_ITEM_ID;

public class NewsListActivity extends BaseActivity {

    private RecyclerView mNewsRV;
    private NewsListVM newsListVM;

    private boolean mTwoPane;
    private List<NewsItem> mNewsList;

    @Inject
    NewsListFactory newsListFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_news_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.newsitem_detail_container) != null) {
            mTwoPane = true;
        }

        mNewsRV = findViewById(R.id.newsitem_list);
        setUpViewModel();
        subscribeObservers();
        if (!mTwoPane && newsListVM.getLastItemId() != DEFAULT_ITEM_ID) {
            openDetailsActivity(newsListVM.getLastItemId());
        }
    }

    @Override
    protected void onNetworkStateChanged(boolean isConnected) {

    }

    private final View.OnClickListener mOnClickListener = view -> {
        long newsId = (long) view.getTag();
        newsListVM.setLastItemId(newsId);
        if (mTwoPane) {
            replaceFragment(R.id.newsitem_detail_container, NewsDetailsFrag.newInstance(newsId),
                    NEWS_DETAILS_TAG, false);
        } else {
            openDetailsActivity(newsId);
        }
    };

    private void openDetailsActivity(long newsId) {
        Intent intent = new Intent(this, NewsDetailsActivity.class);
        intent.putExtra(NEWS_ITEM_ID_KEY, newsId);
        startActivity(intent);
    }

    private Observer<RequestResult> requestResultObserver = requestResult -> {
        if (requestResult != null) {
            switch (requestResult) {
                case IN_PROGRESS:
                    showProgressDialog(null);
                    disableUserActions();
                    break;
                case SUCCESS:
                    dismissProgressDialog();
                    enableUserActions();
                    newsListVM.onResultReceived();
                    break;
                case FAILURE:
                    dismissProgressDialog();
                    enableUserActions();
                    showRequestError();
                    newsListVM.onResultReceived();
                    break;
            }
        }
    };

    private Observer<List<NewsItem>> newsObserver = newsList -> {
        if (newsList != null) {
            mNewsList = newsList;
            setupRecyclerView();
            updateDetailPane();
        }
    };

    private void setUpViewModel() {
        newsListVM = ViewModelProviders.of(this, newsListFactory).get(NewsListVM.class);
    }

    private void setupRecyclerView() {
        NewsAdapter adapter = new NewsAdapter(mNewsList, mOnClickListener);
        mNewsRV.setAdapter(adapter);
    }

    private void updateDetailPane() {
        if (mTwoPane) {
            long lastItemId = newsListVM.getLastItemId();
            if (lastItemId == DEFAULT_ITEM_ID) {
                if (mNewsList.isEmpty())
                    return;
                lastItemId = mNewsList.get(0).getId();
            }
            replaceFragment(R.id.newsitem_detail_container, NewsDetailsFrag.newInstance(lastItemId),
                    NEWS_DETAILS_TAG, false);
        }
    }

    private void subscribeObservers() {
        newsListVM.getRequestResult().observe(this, requestResultObserver);
        newsListVM.getNewsItems().observe(this, newsObserver);
    }

    private void unsubscribeObservers() {
        newsListVM.getRequestResult().removeObserver(requestResultObserver);
        newsListVM.getNewsItems().removeObserver(newsObserver);
    }
}
