package com.hk.newsapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hk.newsapp.R;
import com.hk.newsapp.enums.ContentType;
import com.hk.newsapp.model.NewsItem;
import com.hk.newsapp.ui.activities.GalleryActivity;
import com.hk.newsapp.utils.TimeUtils;
import com.hk.newsapp.vm.NewsDetailsVM;
import com.hk.newsapp.vm.factory.NewsDetailsFactory;

import java.util.Date;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import static com.hk.newsapp.utils.Constants.NEWS_ITEM_ID_KEY;

public class NewsDetailsFrag extends BaseFragment {

    public static NewsDetailsFrag newInstance(long id) {

        Bundle args = new Bundle();
        args.putLong(NEWS_ITEM_ID_KEY, id);
        NewsDetailsFrag fragment = new NewsDetailsFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    NewsDetailsFactory newsDetailsFactory;
    private NewsDetailsVM newsDetailsVM;

    private ImageView mainIV, photoIV, videoIV;
    private TextView titleTV, categoryTV, dateTV, descTV;

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
        subscribeObservers();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unsubscribeObservers();
    }

    private Observer<NewsItem> newsDetailsObserver = newsItem -> {
        if (newsItem != null) {
            NewsDetailsFrag.this.newsItem = newsItem;
            updateUI();
        }
    };

    private View.OnClickListener onClickListener = v -> {
        ContentType contentType = null;
        switch (v.getId()) {
            case R.id.photo_iv_news_details_frag:
                contentType = ContentType.PHOTO;
                break;
            case R.id.video_iv_news_details_frag:
                contentType = ContentType.VIDEO;
                break;
        }
        assert contentType !=null;
        openGalleryActivity(contentType);
    };

    private void initViews(View view) {
        mainIV = view.findViewById(R.id.main_iv_news_details_frag);
        photoIV = view.findViewById(R.id.photo_iv_news_details_frag);
        videoIV = view.findViewById(R.id.video_iv_news_details_frag);
        titleTV = view.findViewById(R.id.title_tv_news_details_frag);
        categoryTV = view.findViewById(R.id.category_tv_news_details_frag);
        dateTV = view.findViewById(R.id.date_tv_news_details_frag);
        descTV = view.findViewById(R.id.desc_tv_news_details_frag);
    }

    private void setUpViewModel() {
        assert getArguments() != null;
        newsDetailsVM = ViewModelProviders.of(this,
                newsDetailsFactory.withId(getArguments().getLong(NEWS_ITEM_ID_KEY)))
                .get(NewsDetailsVM.class);
    }

    private void updateUI() {
        if (getContext() != null) {
            Glide.with(getContext()).load(newsItem.getCoverPhotoUrl()).into(mainIV);
            titleTV.setText(newsItem.getTitle());
            categoryTV.setText(newsItem.getCategory());
            dateTV.setText(TimeUtils.dateToString(new Date(newsItem.getDate())));
            descTV.setText(newsItem.getBody());
            setUpGalleryIcons();
        }
    }

    private void setUpGalleryIcons() {
        if (newsItem.getPhotos() != null && !newsItem.getPhotos().isEmpty()) {
            photoIV.setVisibility(View.VISIBLE);
            photoIV.setOnClickListener(onClickListener);
        }
        if (newsItem.getVideos() != null && !newsItem.getVideos().isEmpty()) {
            videoIV.setVisibility(View.VISIBLE);
            videoIV.setOnClickListener(onClickListener);
        }
    }

    private void subscribeObservers() {
        newsDetailsVM.getNewsItem().observe(this, newsDetailsObserver);
    }

    private void unsubscribeObservers() {
        newsDetailsVM.getNewsItem().removeObserver(newsDetailsObserver);
    }

    private void openGalleryActivity(ContentType contentType) {
        if(getContext()!=null){
            getContext().startActivity(GalleryActivity
                    .getIntent(getContext(), contentType, newsItem.getId()));
        }
    }
}
