package com.hk.newsapp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.hk.newsapp.R;
import com.hk.newsapp.enums.ContentType;
import com.hk.newsapp.ui.fragments.GalleryFragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import static com.hk.newsapp.utils.Constants.GALLERY_TAG;
import static com.hk.newsapp.utils.Constants.NEWS_ITEM_ID_KEY;

public class GalleryActivity extends BaseActivity {

    public static Intent getIntent(Context context, ContentType contentType, long newsItemId) {
        Intent intent = new Intent(context, GalleryActivity.class);
        intent.putExtra(CONTENT_TYPE_KEY, contentType.name());
        intent.putExtra(NEWS_ITEM_ID_KEY, newsItemId);
        return intent;
    }

    public static final String CONTENT_TYPE_KEY = "CONTENT_TYPE_KEY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_news_details);
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState == null) {
            launchGalleryFrag();
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

    private void launchGalleryFrag() {
        String contentType = getIntent().getStringExtra(CONTENT_TYPE_KEY);
        long newsItemId = getIntent().getLongExtra(NEWS_ITEM_ID_KEY, -1);
        addFragment(R.id.newsitem_detail_container,
                GalleryFragment.newInstance(contentType, newsItemId),
                GALLERY_TAG, false);
    }
}
