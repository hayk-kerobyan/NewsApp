package com.hk.newsapp.ui.activities;

import android.os.Bundle;
import android.view.View;

import com.hk.newsapp.R;
import com.hk.newsapp.enums.RequestResult;
import com.hk.newsapp.model.NewsItem;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import static com.hk.newsapp.utils.Constants.DEFAULT_ITEM_ID;
import static com.hk.newsapp.utils.Constants.NEWS_DETAILS_TAG;

public class NewsListActivity extends BaseActivity {

    private NewsListVM newsListVM;

    private SwipeRefreshLayout newsSRL;
    private RecyclerView newsRV;
    private NewsAdapter adapter;

    private List<NewsItem> mNewsList;
    private boolean mTwoPane;

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
        init();
        setUpViewModel();
        subscribeObservers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unsubscribeObservers();
    }

    @Override
    protected void onNetworkStateChanged(boolean isConnected) {

    }

    private final View.OnClickListener mOnClickListener = view -> {
        int position = (int) view.getTag();
        NewsItem newsItem = mNewsList.get(position);
        if (!newsItem.isRead()) {
            newsItem.setRead(true);
            newsListVM.updateNewsItem(newsItem);
            adapter.notifyItemChanged(position);
        }
        long newsId = newsItem.getId();
        newsListVM.setLastItemId(newsId);
        if (mTwoPane) {
            launchDetailsFrag(newsId);
        } else {
            openDetailsActivity(newsId);
        }
    };

    private Observer<RequestResult> requestResultObserver = requestResult -> {
        if (requestResult != null) {
            switch (requestResult) {
                case IN_PROGRESS:
                    newsSRL.setRefreshing(true);
                    break;
                case SUCCESS:
                    newsSRL.setRefreshing(false);
                    newsListVM.onResultReceived();
                    break;
                case FAILURE:
                    newsSRL.setRefreshing(false);
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

    private SwipeRefreshLayout.OnRefreshListener refreshListener = () -> newsListVM.loadNews();

    private void init() {
        newsSRL = findViewById(R.id.news_srl_main_act);
        newsRV = findViewById(R.id.news_rv_main_act);
        newsSRL.setOnRefreshListener(refreshListener);
    }

    private void setUpViewModel() {
        newsListVM = ViewModelProviders.of(this, newsListFactory).get(NewsListVM.class);
    }

    private void setupRecyclerView() {
        adapter = new NewsAdapter(mNewsList, mOnClickListener);
        newsRV.setAdapter(adapter);
    }

    private void updateDetailPane() {
        if (mTwoPane) {
            long lastItemId = newsListVM.getLastItemId();
            if (lastItemId == DEFAULT_ITEM_ID) {
                if (mNewsList.isEmpty())
                    return;
                lastItemId = mNewsList.get(0).getId();
            }
            launchDetailsFrag(lastItemId);
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


    private void launchDetailsFrag(long newsitemId) {
        replaceFragment(R.id.newsitem_detail_container, NewsDetailsFrag.newInstance(newsitemId),
                NEWS_DETAILS_TAG, false);
    }

    private void openDetailsActivity(long newsItemId) {
        startActivity(NewsDetailsActivity.getIntent(this, newsItemId));
    }
}
