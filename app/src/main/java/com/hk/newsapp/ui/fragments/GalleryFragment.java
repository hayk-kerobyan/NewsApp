package com.hk.newsapp.ui.fragments;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hk.newsapp.R;
import com.hk.newsapp.enums.ContentType;
import com.hk.newsapp.model.Photo;
import com.hk.newsapp.model.Video;
import com.hk.newsapp.ui.adapters.PhotosAdapter;
import com.hk.newsapp.ui.adapters.VideoAdapter;
import com.hk.newsapp.vm.GalleryVM;
import com.hk.newsapp.vm.factory.GalleryFactory;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import static com.hk.newsapp.utils.Constants.CONTENT_TYPE_KEY;
import static com.hk.newsapp.utils.Constants.DEFAULT_ITEM_ID;
import static com.hk.newsapp.utils.Constants.NEWS_ITEM_ID_KEY;

public class GalleryFragment extends BaseFragment {

    public static GalleryFragment newInstance(String contentType, long newsItemId) {
        Bundle args = new Bundle();
        args.putString(CONTENT_TYPE_KEY, contentType);
        args.putLong(NEWS_ITEM_ID_KEY, newsItemId);
        GalleryFragment fragment = new GalleryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    GalleryFactory galleryFactory;
    private GalleryVM galleryVM;

    private RecyclerView contentRV;
    private PhotosAdapter photosAdapter;
    private VideoAdapter videoAdapter;

    private ContentType contentType;
    private List<Photo> photos;
    private List<Video> videos;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_gallery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setUpViewModel();
        subscribeObservers();
    }

    private Observer<List<Photo>> photosObserver = photos -> {
        if (photos != null) {
            this.photos = photos;
            updateUIForPhotos();
        }
    };
    private Observer<List<Video>> videosObserver = videos -> {
        if (videos != null) {
            this.videos = videos;
            updateUIForVideos();
        }
    };

    private View.OnClickListener onClickListener = v -> {
        int pos = (int) v.getTag();

    };

    private void initViews(View view) {
        contentRV = view.findViewById(R.id.content_rv_gallery_frag);
    }

    private void setUpViewModel() {
        assert getArguments() != null;
        contentType = ContentType.valueOf(getArguments().getString(CONTENT_TYPE_KEY));
        long newsItemId = getArguments().getLong(NEWS_ITEM_ID_KEY, DEFAULT_ITEM_ID);
        galleryVM = ViewModelProviders.of(this, galleryFactory
                .withId(contentType, newsItemId)).get(GalleryVM.class);
    }

    private void updateUIForPhotos() {
        if (getContext() != null) {
            if (photosAdapter == null) {
                photosAdapter = new PhotosAdapter(photos, onClickListener);
            }
            setUpRecyclerView(photosAdapter);
        }
    }

    private void updateUIForVideos() {
        if (getContext() != null) {
            if (videoAdapter == null) {
                videoAdapter = new VideoAdapter(videos, onClickListener);
            }
            setUpRecyclerView(videoAdapter);
        }
    }

    private void setUpRecyclerView(RecyclerView.Adapter adapter){
        int spanCount = isOrientationVertical() ? 2 : 4;
        StaggeredGridLayoutManager lm = new StaggeredGridLayoutManager
                (spanCount, StaggeredGridLayoutManager.VERTICAL);
        contentRV.setLayoutManager(lm);
        contentRV.setAdapter(adapter);
    }

    private void subscribeObservers() {
        switch (contentType) {
            case PHOTO:
                galleryVM.getPhotos().observe(this, photosObserver);
                break;
            case VIDEO:
                galleryVM.getVideos().observe(this, videosObserver);
                break;
            default:
                throw new IllegalStateException("Unhandled content type.");
        }
    }

    private boolean isOrientationVertical() {
        assert getContext() != null;
        return getContext().getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT;

    }
}
