package com.hk.newsapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hk.newsapp.R;
import com.hk.newsapp.model.NewsItem;

import java.util.List;

import androidx.annotation.NonNull;
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

        private TextView titleTV, subCategoryTV;
        private ImageView mainIV;

        NewsVH(final View itemView) {
            super(itemView);
            initViews(itemView);
            if(onClickListener!=null){
                itemView.setOnClickListener(onClickListener);
            }
        }

        private void initViews(View itemView) {
            titleTV = itemView.findViewById(R.id.title_tv_news_item);
//            mainIV = itemView.findViewById(R.id.main_iv_product_item);
        }

        public void setData(NewsItem newsItem) {
            itemView.setTag(newsList.get(getAdapterPosition()).getId());
            titleTV.setText(newsItem.getTitle());
//            Glide.with(itemView.getContext()).load(new.getImageUrls().get(0))
//                    .apply(requestOptions).into(mainIV);
        }
    }
}