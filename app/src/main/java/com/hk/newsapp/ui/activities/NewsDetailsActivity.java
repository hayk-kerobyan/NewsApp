package com.hk.newsapp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.hk.newsapp.R;
import com.hk.newsapp.ui.fragments.NewsDetailsFrag;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import static com.hk.newsapp.utils.Constants.DEFAULT_ITEM_ID;
import static com.hk.newsapp.utils.Constants.NEWS_DETAILS_TAG;
import static com.hk.newsapp.utils.Constants.NEWS_ITEM_ID_KEY;

public class NewsDetailsActivity extends BaseActivity {

    public static Intent getIntent(Context context, long newsItemId) {
        Intent intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra(NEWS_ITEM_ID_KEY, newsItemId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getBoolean(R.bool.two_pane_screen)) {
            finish();
            return;
        }
        setContentView(R.layout.act_news_details);
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        networkStateTV = findViewById(R.id.network_state_tv);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState == null) {
            launchDetailsFrag();
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

    private void launchDetailsFrag() {
        addFragment(R.id.newsitem_detail_container, NewsDetailsFrag
                        .newInstance(getIntent().getLongExtra(NEWS_ITEM_ID_KEY, DEFAULT_ITEM_ID)),
                NEWS_DETAILS_TAG, false);
    }
}
