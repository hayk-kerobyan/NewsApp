package com.hk.newsapp.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;

import com.hk.newsapp.R;
import com.hk.newsapp.ui.fragments.NewsDetailsFrag;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import static com.hk.newsapp.ui.fragments.NewsDetailsFrag.NEWS_ITEM_ID_KEY;

public class NewsDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getBoolean(R.bool.two_pane_screen)) {
            finish();
            return;
        }
        setContentView(R.layout.act_news_details);
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState == null) {
            lauchDetailsFrag();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNetworkStateChanged(boolean isConnected) {

    }

    private void lauchDetailsFrag() {
        addFragment(R.id.newsitem_detail_container, NewsDetailsFrag
                        .newInstance(getIntent().getLongExtra(NEWS_ITEM_ID_KEY, -1)),
                NewsDetailsFrag.NEWS_DETAILS_TAG, false);
    }
}
