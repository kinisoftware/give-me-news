package com.kinisoftware.givemenews.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {

    public Response response;

    public static class Response {

        public List<Article> docs;

        public static class Article {
            @SerializedName("web_url")
            public String webUrl;

            public Headline headline;

            @SerializedName("multimedia")
            public List<Multimedia> multimedias;

            public String getThumbnail() {
                if (multimedias != null) {
                    if (!multimedias.isEmpty()) {
                        return "http://www.nytimes.com/" + multimedias.get(0).url;
                    }
                }
                return null;
            }

            public static class Headline {
                public String main;
            }

            public static class Multimedia {
                public String url;
                public String subtype;
            }
        }
    }
}
