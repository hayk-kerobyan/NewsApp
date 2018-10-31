package com.hk.newsapp.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hk.newsapp.R;
import com.hk.newsapp.interfaces.IEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NewsDetailsFrag extends BaseFragment {

    public static NewsDetailsFrag newInstance(long id) {

        Bundle args = new Bundle();
        args.putLong(NEWS_ITEM_ID_KEY, id);
        NewsDetailsFrag fragment = new NewsDetailsFrag();
        fragment.setArguments(args);
        return fragment;
    }

    public static final String NEWS_ITEM_ID_KEY = "NEWS_ITEM_ID_KEY";
    public static final String NEWS_DETAILS_TAG = "NEWS_DETAILS_TAG";

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
    }

    private void initViews(View view) {
        TextView textView = view.findViewById(R.id.textView);
        textView.setText("" + getArguments().getLong(NEWS_ITEM_ID_KEY));
    }

    private void updateUI() {

    }


}
