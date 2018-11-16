package com.hk.newsapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hk.newsapp.R;
import com.hk.newsapp.databinding.FragNewsDetailsBinding;
import com.hk.newsapp.enums.ContentType;
import com.hk.newsapp.ui.activities.GalleryActivity;
import com.hk.newsapp.vm.NewsDetailsVM;
import com.hk.newsapp.vm.factory.NewsDetailsFactory;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
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

    private FragNewsDetailsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_news_details,
                container, false);
        binding.setPresenter(presenter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViewModel();
    }

    private Presenter presenter = (view, id) -> {
        ContentType contentType = null;
        switch (view.getId()) {
            case R.id.photo_iv_news_details_frag:
                contentType = ContentType.PHOTO;
                break;
            case R.id.video_iv_news_details_frag:
                contentType = ContentType.VIDEO;
                break;
        }
        assert contentType != null;
        startActivity(GalleryActivity.getIntent(getContext(), contentType, id));
    };

    private void setUpViewModel() {
        assert getArguments() != null;
        NewsDetailsVM newsDetailsVM = ViewModelProviders.of(this,
                newsDetailsFactory.withId(getArguments().getLong(NEWS_ITEM_ID_KEY)))
                .get(NewsDetailsVM.class);
        newsDetailsVM.getNewsItem().observe(getViewLifecycleOwner(), newsItem -> {
            if (newsItem != null) binding.setNewsItem(newsItem);
        });
    }

    public interface Presenter {
        void onGalleryRequested(View view, long id);
    }
}
