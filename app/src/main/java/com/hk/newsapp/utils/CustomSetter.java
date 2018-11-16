package com.hk.newsapp.utils;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.databinding.BindingAdapter;

public class CustomSetter {

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).into(view);
    }

    @BindingAdapter("listVisibility")
    public static void listToVisibility(View view, List list) {
        view.setVisibility((list == null || list.isEmpty()) ? View.GONE : View.VISIBLE);
    }
}
