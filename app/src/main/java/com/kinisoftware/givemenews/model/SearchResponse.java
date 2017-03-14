package com.kinisoftware.givemenews.model;

public class SearchResponse {

    Response response;

    public SearchResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }
}
