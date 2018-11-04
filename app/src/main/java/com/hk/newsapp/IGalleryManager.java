package com.hk.newsapp;

public interface IGalleryManager {
    void onPhotoItemSelected(long newsItemId, long contentId);
    void onVideoItemSelected(long newsItemId, long contentId);
}
