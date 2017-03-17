package com.kinisoftware.givemenews.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Article {

    @SerializedName("web_url")
    String webUrl;

    Headline headline;

    @SerializedName("multimedia")
    List<Multimedia> multimedias;

    public Article(String webUrl, Headline headline, List<Multimedia> multimedias) {
        this.webUrl = webUrl;
        this.headline = headline;
        this.multimedias = multimedias;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public Headline getHeadline() {
        return headline;
    }

    public List<Multimedia> getMultimedias() {
        return multimedias;
    }

    public Multimedia getThumbnail() {
        if (multimedias != null) {
            for (Multimedia multimedia : multimedias) {
                if (multimedia.getSubtype().equals("thumbnail")) {
                    return multimedia;
                }
            }
        }
        return null;
    }
}
