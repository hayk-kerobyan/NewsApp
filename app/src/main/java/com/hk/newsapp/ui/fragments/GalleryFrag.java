package com.hk.newsapp.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hk.newsapp.interfaces.IGalleryManager;
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
import static com.hk.newsapp.utils.Constants.YOUTUBE_BASE_URL;

public class GalleryFrag extends BaseFragment {

    public static GalleryFrag newInstance(String contentType, long newsItemId) {
        Bundle args = new Bundle();
        args.putString(CONTENT_TYPE_KEY, contentType);
        args.putLong(NEWS_ITEM_ID_KEY, newsItemId);
        GalleryFrag fragment = new GalleryFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    GalleryFactory galleryFactory;
    private GalleryVM galleryVM;
    private IGalleryManager galleryManager;

    private RecyclerView contentRV;
    private PhotosAdapter photosAdapter;
    private VideoAdapter videoAdapter;

    private long newsItemId;
    private ContentType contentType;
    private List<Photo> photos;
    private List<Video> videos;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            galleryManager = (IGalleryManager) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement IGalleryManager");
        }
    }

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
        retrieveArgs();
        initViews(view);
        setUpViewModel();
        subscribeObservers();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unsubscribeObservers();
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
        if (contentType == ContentType.PHOTO) {
            galleryManager.onPhotoItemSelected(newsItemId, photos.get(pos).getId());
        } else if (contentType == ContentType.VIDEO) {
            openVideo(videos.get(pos).getYoutubeId());
        } else throw new IllegalStateException("Unhandled content type");
    };

    private void initViews(View view) {
        contentRV = view.findViewById(R.id.content_rv_gallery_frag);
    }

    private void retrieveArgs() {
        assert getArguments() != null;
        contentType = ContentType.valueOf(getArguments().getString(CONTENT_TYPE_KEY));
        newsItemId = getArguments().getLong(NEWS_ITEM_ID_KEY, DEFAULT_ITEM_ID);
    }

    private void setUpViewModel() {
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

    private void setUpRecyclerView(RecyclerView.Adapter adapter) {
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

    private void unsubscribeObservers() {
        switch (contentType) {
            case PHOTO:
                galleryVM.getPhotos().removeObserver(photosObserver);
                break;
            case VIDEO:
                galleryVM.getVideos().removeObserver(videosObserver);
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

    private void openVideo(String youtubeId) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_BASE_URL + youtubeId)));
    }
}
