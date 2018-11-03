package com.hk.newsapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "newsItem")
public class NewsItem {

    public NewsItem() {}

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo
    private boolean read;

    @SerializedName("category")
    @Expose
    @ColumnInfo
    private String category;

    @SerializedName("title")
    @Expose
    @ColumnInfo
    private String title;

    @SerializedName("body")
    @Expose
    @ColumnInfo
    private String body;

    @SerializedName("shareUrl")
    @Expose
    @ColumnInfo
    private String shareUrl;

    @SerializedName("coverPhotoUrl")
    @Expose
    @ColumnInfo
    private String coverPhotoUrl;

    @SerializedName("date")
    @Expose
    @ColumnInfo
    private long date;

    @SerializedName("gallery")
    @Expose
    @Ignore
    private List<Photo> photos;

    @SerializedName("video")
    @Expose
    @Ignore
    private List<Video> videos;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getCoverPhotoUrl() {
        return coverPhotoUrl;
    }

    public void setCoverPhotoUrl(String coverPhotoUrl) {
        this.coverPhotoUrl = coverPhotoUrl;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}
