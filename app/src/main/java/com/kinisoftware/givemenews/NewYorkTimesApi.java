package com.kinisoftware.givemenews;

import com.kinisoftware.givemenews.model.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewYorkTimesApi {

    @GET("search/v2/articlesearch.json")
    Call<SearchResponse> search(@Query("api-key") String apiKey, @Query("page") int page, @Query("q") String query);
}
