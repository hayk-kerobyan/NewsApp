package com.hk.newsapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hk.newsapp.model.newsitem.NewsItem;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "photo",
        foreignKeys = @ForeignKey(entity = NewsItem.class,
                parentColumns = "id",
                childColumns = "newsItemId",
                onDelete = CASCADE),
        indices = {@Index("newsItemId")})
public class Photo {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo
    private long newsItemId;

    @SerializedName("title")
    @Expose
    @ColumnInfo
    private String title;

    @SerializedName("thumbnailUrl")
    @Expose
    @ColumnInfo
    private String thumbnailUrl;

    @SerializedName("contentUrl")
    @Expose
    @ColumnInfo
    private String contentUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNewsItemId() {
        return newsItemId;
    }

    public void setNewsItemId(long newsItemId) {
        this.newsItemId = newsItemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }
}
