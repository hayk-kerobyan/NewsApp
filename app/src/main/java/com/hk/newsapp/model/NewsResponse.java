package com.hk.newsapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("errors")
    @Expose
    private List<Object> errors;

    @SerializedName("data")
    @Expose
    private List<NewsItem> data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

    public List<NewsItem> getData() {
        return data;
    }

    public void setData(List<NewsItem> data) {
        this.data = data;
    }
}
