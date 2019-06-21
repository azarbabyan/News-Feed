package com.example.newsfeed.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.newsfeed.R;
import com.example.newsfeed.network.data.Result;
import com.example.newsfeed.ui.adapters.FeedAdapter;
import com.example.newsfeed.viewmodels.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.news_feed_recycler);
        final FeedAdapter adapter = new FeedAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);
        viewModel.getNews(1).observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {
                adapter.updateList(results);
            }
        });
    }
}
