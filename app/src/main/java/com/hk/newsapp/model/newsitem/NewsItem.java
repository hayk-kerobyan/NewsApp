package com.hk.newsapp.model.newsitem;

public class NewsItem {

    public NewsItem(long id, String title) {
        this.id = id;
        this.title = title;
    }

    private long id;
    private String title;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
