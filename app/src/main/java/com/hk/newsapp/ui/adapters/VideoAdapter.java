package com.hk.newsapp.ui.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.hk.newsapp.R;
import com.hk.newsapp.model.Video;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    public VideoAdapter(@NonNull List<Video> videos, View.OnClickListener clickListener) {
        requestOptions = new RequestOptions().placeholder(R.drawable.ic_video).centerCrop();
        this.clickListener = clickListener;
        this.videos = videos;
    }

    private List<Video> videos;
    private RequestOptions requestOptions;
    private View.OnClickListener clickListener;

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gallery, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.setData(videos.get(position));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public void setData(List<Video> videos) {
        this.videos = videos;
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {

        private ImageView thumbnailIV;
        private ProgressBar progressBar;

        VideoViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
            itemView.setOnClickListener(clickListener);
        }

        private void initViews(View itemView) {
            thumbnailIV = itemView.findViewById(R.id.thumbnail_iv_gallery_item);
            progressBar = itemView.findViewById(R.id.progress_bar_gallery_item);
        }

        private void setData(Video video) {
            itemView.setTag(getAdapterPosition());
            Glide.with(itemView.getContext())
                    .load(video.getThumbnailUrl())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e,
                                                    Object model,
                                                    Target<Drawable> target,
                                                    boolean isFirstResource) {
                            progressBar.setVisibility(View.VISIBLE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource,
                                                       Object model,
                                                       Target<Drawable> target,
                                                       DataSource dataSource,
                                                       boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .apply(requestOptions)
                    .into(thumbnailIV);
        }
    }
}