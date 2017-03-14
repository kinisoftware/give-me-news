package com.kinisoftware.givemenews.model;

import java.util.List;

public class Response {

    List<Article> docs;

    public Response(List<Article> docs) {
        this.docs = docs;
    }

    public List<Article> getDocs() {
        return docs;
    }
}
