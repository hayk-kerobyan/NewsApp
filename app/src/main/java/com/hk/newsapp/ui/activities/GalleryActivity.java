package com.hk.newsapp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.hk.newsapp.interfaces.IGalleryManager;
import com.hk.newsapp.R;
import com.hk.newsapp.enums.ContentType;
import com.hk.newsapp.ui.fragments.GalleryFrag;
import com.hk.newsapp.ui.fragments.PhotoPreviewFrag;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import static com.hk.newsapp.utils.Constants.DEFAULT_ITEM_ID;
import static com.hk.newsapp.utils.Constants.GALLERY_TAG;
import static com.hk.newsapp.utils.Constants.NEWS_ITEM_ID_KEY;
import static com.hk.newsapp.utils.Constants.PHOTO_PREVIEW_TAG;

public class GalleryActivity extends BaseActivity implements IGalleryManager {

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

        setContentView(R.layout.act_gallery);
        networkStateTV = findViewById(R.id.network_state_tv);
        Toolbar toolbar = findViewById(R.id.gallery_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState == null) {
            launchGalleryFrag();
        } else {
            Fragment fragment = getSupportFragmentManager()
                    .findFragmentById(R.id.gallery_container);
            if (!(fragment instanceof GalleryFrag)) {
                hideStatusBar();
            }
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

    @Override
    public void onPhotoItemSelected(long newsItemId, long contentId) {
        hideStatusBar();
        launchPhotoPreviewFrag(newsItemId, contentId);
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()>0){
            showStatusBar();
        }
        super.onBackPressed();
    }

    private void launchGalleryFrag() {
        String contentType = getIntent().getStringExtra(CONTENT_TYPE_KEY);
        long newsItemId = getIntent().getLongExtra(NEWS_ITEM_ID_KEY, DEFAULT_ITEM_ID);
        addFragment(R.id.gallery_container,
                GalleryFrag.newInstance(contentType, newsItemId),
                GALLERY_TAG, false);
    }

    private void launchPhotoPreviewFrag(long newsItemId, long contentId) {
        replaceFragment(R.id.gallery_container, PhotoPreviewFrag
                .newInstance(newsItemId, contentId), PHOTO_PREVIEW_TAG, true);
    }

    private void hideStatusBar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void showStatusBar() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(getSupportActionBar()!=null){
            getSupportActionBar().show();
        }
    }
}
