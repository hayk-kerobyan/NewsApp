package com.hk.newsapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hk.newsapp.R;
import com.hk.newsapp.model.Photo;
import com.hk.newsapp.ui.adapters.PhotoPreviewAdapter;
import com.hk.newsapp.vm.GalleryVM;
import com.hk.newsapp.vm.factory.GalleryFactory;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import static com.hk.newsapp.enums.ContentType.PHOTO;
import static com.hk.newsapp.utils.Constants.CONTENT_ID_KEY;
import static com.hk.newsapp.utils.Constants.DEFAULT_ITEM_ID;
import static com.hk.newsapp.utils.Constants.NEWS_ITEM_ID_KEY;

public class PhotoPreviewFrag extends BaseFragment {

    public static PhotoPreviewFrag newInstance(long newsItemId, long contentId) {
        Bundle args = new Bundle();
        args.putLong(CONTENT_ID_KEY, contentId);
        args.putLong(NEWS_ITEM_ID_KEY, newsItemId);
        PhotoPreviewFrag fragment = new PhotoPreviewFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    GalleryFactory galleryFactory;
    private GalleryVM galleryVM;

    private ViewPager photosVP;
    private PhotoPreviewAdapter photosAdapter;

    private List<Photo> photos;
    private long contentId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_photo_preview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        long newsItemId = getArguments().getLong(NEWS_ITEM_ID_KEY, DEFAULT_ITEM_ID);
        if (savedInstanceState == null) {
            contentId = getArguments().getLong(CONTENT_ID_KEY);
        } else {
            contentId = savedInstanceState.getLong(CONTENT_ID_KEY);
        }
        initViews(view);
        setUpViewModel(newsItemId);
        subscribeObservers();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(CONTENT_ID_KEY, contentId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unsubscribeObservers();
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            contentId = photos.get(position).getId();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private Observer<List<Photo>> photosObserver = photos -> {
        if (photos != null) {
            this.photos = photos;
            updateUI();
        }
    };

    private void initViews(View view) {
        photosVP = view.findViewById(R.id.vp_photo_preview_frag);
        view.findViewById(R.id.close_iv_photo_preview_frag).setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
        });
    }

    private void setUpViewModel(long newsItemId) {
        galleryVM = ViewModelProviders.of(this, galleryFactory
                .withId(PHOTO, newsItemId)).get(GalleryVM.class);
    }

    private void updateUI() {
        if (getContext() != null) {
            int selectedPos = getSelectedPosition();
            if (photosAdapter == null) {
                photosAdapter = new PhotoPreviewAdapter(photos);
            }
            photosVP.setAdapter(photosAdapter);
            photosVP.setCurrentItem(selectedPos);
            photosVP.addOnPageChangeListener(pageChangeListener);
        }
    }

    private int getSelectedPosition() {
        for (int i = 0; i < photos.size(); i++) {
            if (contentId == photos.get(i).getId()) {
                return i;
            }
        }
        return 0;
    }

    private void subscribeObservers() {
        galleryVM.getPhotos().observe(this, photosObserver);
    }

    private void unsubscribeObservers() {
        galleryVM.getPhotos().removeObserver(photosObserver);
    }
}
