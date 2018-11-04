package com.hk.newsapp.utils;

import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public class ImageUtils {

    public static void loadWithHorizontalRatio(final ImageView imageView, final String imageUrl) {
        if (imageView.getHeight() == 0) {
            imageView.getViewTreeObserver().addOnPreDrawListener(
                    new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                            loadWithHorizontalRatio(imageView, imageUrl);
                            return true;
                        }
                    });
        } else {
            Glide.with(imageView.getContext())
                    .load(imageUrl)
                    .apply(new RequestOptions()
                            .fitCenter()
                            .override(Target.SIZE_ORIGINAL, imageView.getHeight()))
                    .into(imageView);
        }
    }
}
