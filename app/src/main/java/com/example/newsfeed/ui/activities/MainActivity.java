package com.example.newsfeed.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.newsfeed.R;
import com.example.newsfeed.listeners.OnloadMoreListener;
import com.example.newsfeed.ui.adapters.FeedAdapter;
import com.example.newsfeed.viewmodels.MainActivityViewModel;

public class MainActivity extends AppCompatActivity implements OnloadMoreListener {

    private MainActivityViewModel viewModel;
    private int totalItemCounts = 100;
    private int startPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.news_feed_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        FeedAdapter adapter = new FeedAdapter(recyclerView, this);
        recyclerView.setAdapter(adapter);
        viewModel.getNewsList().observe(this, adapter::submitList);
        getNews(startPage);
    }

    public int getDBCurrentListSize() {
        return viewModel.currentDBListSize();
    }

    public int getTotalItemCounts() {
        return totalItemCounts;
    }

    @Override
    public void onLoadMore() {
        startPage++;
        getNews(startPage);
    }

    private void getNews(Integer startPage) {
        viewModel.getNews(startPage).observe(this, newsResponseResource -> {
            switch (newsResponseResource.status) {
                case SUCCESS:
                    boolean isSuccess = false;
                    if (newsResponseResource.data != null) {
                        if (newsResponseResource.data.getResponse() != null) {
                            if (newsResponseResource.data.getResponse().getResults() != null) {
                                isSuccess = viewModel.insertResultsToDb(newsResponseResource.data.getResponse().getResults());
                            }
                        }
                    }
                    if (isSuccess) {
                        totalItemCounts = getDBCurrentListSize();
                    }
                    break;
                case ERROR:
                    break;
                case LOADING:
                    break;
            }
        });
    }
}
