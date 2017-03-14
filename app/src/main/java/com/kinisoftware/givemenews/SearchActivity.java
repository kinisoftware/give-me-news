package com.kinisoftware.givemenews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kinisoftware.givemenews.model.Article;
import com.kinisoftware.givemenews.model.SearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.nytimes.com/svc/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewYorkTimesApi nytApi = retrofit.create(NewYorkTimesApi.class);
        Call<SearchResponse> search = nytApi.search(getString(R.string.nyt_api_key), 0, "android");

        search.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, retrofit2.Response<SearchResponse> response) {
                List<Article> docs = response.body().getResponse().getDocs();
                System.out.println(docs);
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {

            }
        });
    }
}
