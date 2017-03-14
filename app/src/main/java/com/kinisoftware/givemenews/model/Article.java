package com.kinisoftware.givemenews.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Article {

    @SerializedName("web_url")
    String webUrl;

    Headline headline;

    List<Multimedia> multimedia;

    public Article(String webUrl, Headline headline, List<Multimedia> multimedia) {
        this.webUrl = webUrl;
        this.headline = headline;
        this.multimedia = multimedia;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public Headline getHeadline() {
        return headline;
    }

    public List<Multimedia> getMultimedia() {
        return multimedia;
    }
}
