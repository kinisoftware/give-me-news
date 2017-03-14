package com.kinisoftware.givemenews.model;

public class Multimedia {

    String url;
    String subtype;

    public Multimedia(String url, String subtype) {
        this.url = url;
        this.subtype = subtype;
    }

    public String getUrl() {
        return url;
    }

    public String getSubtype() {
        return subtype;
    }
}
