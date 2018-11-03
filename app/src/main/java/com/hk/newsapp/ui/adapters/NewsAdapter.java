package com.hk.newsapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.hk.newsapp.R;
import com.hk.newsapp.model.NewsItem;
import com.hk.newsapp.utils.ImageUtils;
import com.hk.newsapp.utils.TimeUtils;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsVH> {

    private List<NewsItem> newsList;
//    private RequestOptions requestOptions = new RequestOptions().centerCrop();
    private View.OnClickListener onClickListener;

    public NewsAdapter(List<NewsItem> newsList, View.OnClickListener onClickListener) {
        this.newsList = newsList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public NewsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        return new NewsVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsVH holder, int position) {
        holder.setData(newsList.get(position));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void setData(List<NewsItem> newsList) {
        this.newsList = newsList;
    }

    class NewsVH extends RecyclerView.ViewHolder {

        private ImageView mainIV;
        private TextView titleTV, categoryTV, dateTV;

        NewsVH(final View itemView) {
            super(itemView);
            initViews(itemView);
            if(onClickListener!=null){
                itemView.setOnClickListener(onClickListener);
            }
        }

        private void initViews(View itemView) {
            mainIV = itemView.findViewById(R.id.main_iv_news_item);
            titleTV = itemView.findViewById(R.id.title_tv_news_item);
            categoryTV = itemView.findViewById(R.id.category_tv_news_item);
            dateTV = itemView.findViewById(R.id.date_tv_news_item);
        }

        public void setData(NewsItem newsItem) {
            itemView.setTag(getAdapterPosition());
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(),
                    newsItem.isRead()?R.color.readNewsItemBG:R.color.unreadNewsItemBG));
            ImageUtils.loadWithHorizontalRatio(mainIV, newsItem.getCoverPhotoUrl());
            titleTV.setText(newsItem.getTitle());
            categoryTV.setText(newsItem.getCategory());
            dateTV.setText(TimeUtils.dateToString(new Date(newsItem.getDate())));
        }
    }
}