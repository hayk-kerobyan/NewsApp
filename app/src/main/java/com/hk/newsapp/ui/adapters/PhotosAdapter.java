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
import com.hk.newsapp.model.Photo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder> {

    public PhotosAdapter(@NonNull List<Photo> photos, View.OnClickListener clickListener) {
        requestOptions = new RequestOptions().placeholder(R.drawable.ic_photo);
        this.clickListener = clickListener;
        this.photos = photos;
    }

    private List<Photo> photos;
    private RequestOptions requestOptions;
    private View.OnClickListener clickListener;

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gallery, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        holder.setData(photos.get(position));
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void setData(List<Photo> photos) {
        this.photos = photos;
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {

        private ImageView thumbnailIV;
        private ProgressBar progressBar;

        PhotoViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
            itemView.setOnClickListener(clickListener);
        }

        private void initViews(View itemView) {
            thumbnailIV = itemView.findViewById(R.id.thumbnail_iv_gallery_item);
            progressBar = itemView.findViewById(R.id.progress_bar_gallery_item);
        }

        private void setData(Photo photo) {
            itemView.setTag(getAdapterPosition());
            Glide.with(itemView.getContext())
                    .load(photo.getThumbnailUrl())
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