package com.hk.newsapp.model.newsitem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hk.newsapp.model.Gallery;
import com.hk.newsapp.model.Video;

import java.util.List;

public class NewsItem {

    public NewsItem() {}

    public NewsItem(long id, String title) {
        this.id = id;
        this.title = title;
    }

    private long id;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("body")
    @Expose
    private String body;

    @SerializedName("shareUrl")
    @Expose
    private String shareUrl;

    @SerializedName("coverPhotoUrl")
    @Expose
    private String coverPhotoUrl;

    @SerializedName("date")
    @Expose
    private Integer date;

    @SerializedName("gallery")
    @Expose
    private List<Gallery> gallery = null;

    @SerializedName("video")
    @Expose
    private List<Video> video = null;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public List<Gallery> getGallery() {
        return gallery;
    }

    public void setGallery(List<Gallery> gallery) {
        this.gallery = gallery;
    }

    public List<Video> getVideo() {
        return video;
    }

    public void setVideo(List<Video> video) {
        this.video = video;
    }
}
