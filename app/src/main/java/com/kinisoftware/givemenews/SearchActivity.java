package com.kinisoftware.givemenews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.kinisoftware.givemenews.model.SearchFilters;
import com.kinisoftware.givemenews.model.SearchResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_FILTERS = 0;

    @BindView((R.id.etSearch))
    EditText etSearch;
    @BindView(R.id.gVNews)
    RecyclerView gvNews;
    private List<SearchResponse.Response.Article> articles;
    private ArticlesAdapter articlesAdapter;
    private SearchFilters filters;
    private EndlessRecyclerViewScrollListener scrollListener;

    @OnClick(R.id.btSearch)
    void search() {
        articles.clear();
        articlesAdapter.notifyDataSetChanged();
        scrollListener.resetState();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.nytimes.com/svc/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewYorkTimesApi nytApi = retrofit.create(NewYorkTimesApi.class);

        Call<SearchResponse> search;
        if (filters.shouldFilter()) {
            Logger.getAnonymousLogger().info("Search with filters");
            search = nytApi.search(
                    getString(R.string.nyt_api_key),
                    0,
                    etSearch.getText().toString(),
                    filters.getBeginDate(),
                    filters.getSortOrder(),
                    filters.getDeskValues());
        } else {
            Logger.getAnonymousLogger().info("Search without filters");
            search = nytApi.search(getString(R.string.nyt_api_key), 0, etSearch.getText().toString());
        }

        search.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, retrofit2.Response<SearchResponse> response) {
                SearchResponse searchResponse = response.body();
                if (searchResponse != null && searchResponse.status.equals("OK")) {
                    articles.addAll(searchResponse.response.docs);
                    articlesAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "No results", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {

            }
        });
    }

    public void showFilters(MenuItem mi) {
        Intent intentAddTaskActivity = new Intent(SearchActivity.this, FilterActivity.class);
        startActivityForResult(intentAddTaskActivity, REQUEST_CODE_FILTERS);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        filters = new SearchFilters();
        articles = new ArrayList<>();
        articlesAdapter = new ArticlesAdapter(articles, this);
        gvNews.setAdapter(articlesAdapter);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        gvNews.setLayoutManager(layoutManager);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextDataFromApi(page);
            }
        };
        gvNews.addOnScrollListener(scrollListener);
    }

    public void loadNextDataFromApi(int offset) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.nytimes.com/svc/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewYorkTimesApi nytApi = retrofit.create(NewYorkTimesApi.class);

        Call<SearchResponse> search;
        if (filters.shouldFilter()) {
            Logger.getAnonymousLogger().info("Search with filters");
            search = nytApi.search(
                    getString(R.string.nyt_api_key),
                    0,
                    etSearch.getText().toString(),
                    filters.getBeginDate(),
                    filters.getSortOrder(),
                    filters.getDeskValues());
        } else {
            Logger.getAnonymousLogger().info("Search without filters");
            search = nytApi.search(getString(R.string.nyt_api_key), offset, etSearch.getText().toString());
        }

        search.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, retrofit2.Response<SearchResponse> response) {
                SearchResponse searchResponse = response.body();
                if (searchResponse != null && searchResponse.status.equals("OK")) {
                    articles.addAll(searchResponse.response.docs);
                    articlesAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "No results", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_FILTERS) {
            filters = (SearchFilters) data.getExtras().getSerializable("filters");
            Toast.makeText(this, "Filters saved", Toast.LENGTH_SHORT).show();
        }
    }
}
