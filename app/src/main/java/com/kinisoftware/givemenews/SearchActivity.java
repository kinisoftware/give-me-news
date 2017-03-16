package com.kinisoftware.givemenews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.kinisoftware.givemenews.model.Article;
import com.kinisoftware.givemenews.model.SearchResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {
    @BindView((R.id.etSearch))
    EditText etSearch;
    @BindView(R.id.gVNews)
    GridView gvNews;
    private List<Article> articles;
    private ArticlesAdapter articlesAdapter;

    @OnClick(R.id.btSearch)
    void search() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.nytimes.com/svc/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewYorkTimesApi nytApi = retrofit.create(NewYorkTimesApi.class);
        Call<SearchResponse> search = nytApi.search(getString(R.string.nyt_api_key), 0, etSearch.getText().toString());

        search.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, retrofit2.Response<SearchResponse> response) {
                articles.addAll(response.body().getResponse().getDocs());
                articlesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        articles = new ArrayList<>();
        articlesAdapter = new ArticlesAdapter(this, articles);
        gvNews.setAdapter(articlesAdapter);
    }
}
