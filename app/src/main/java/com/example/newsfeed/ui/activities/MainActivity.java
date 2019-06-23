package com.example.newsfeed.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.newsfeed.R;
import com.example.newsfeed.data.eventbusmodels.UpdateDbEvent;
import com.example.newsfeed.listeners.OnloadMoreListener;
import com.example.newsfeed.ui.adapters.FeedAdapter;
import com.example.newsfeed.ui.adapters.PinnedNewsAdapter;
import com.example.newsfeed.viewmodels.MainActivityViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.example.newsfeed.utils.AppConstants.USER_ID;

public class MainActivity extends AppCompatActivity implements OnloadMoreListener {

    private MainActivityViewModel viewModel;

    private FeedAdapter adapter;
    private RecyclerView recyclerView, pinnedRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new FeedAdapter(recyclerView, this);
        recyclerView.setAdapter(adapter);
        viewModel.getNewsList().observe(this, adapter::submitList);
        PinnedNewsAdapter pinnedNewsAdapter = new PinnedNewsAdapter();
        pinnedRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        pinnedRecyclerView.setAdapter(pinnedNewsAdapter);
        viewModel.getPinnedNews().observe(this, pinnedNewsAdapter::submitList);
        viewModel.setStartPage(1);
        getNews(viewModel.getStartPage());
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void bindViews() {
        recyclerView = findViewById(R.id.news_feed_recycler);
        pinnedRecyclerView = findViewById(R.id.pined_recyclerview);
    }

    public int getDBCurrentListSize() {
        return viewModel.currentDBListSize();
    }

    public int getTotalItemCounts() {
        return viewModel.getTotalItemCounts();
    }

    @Override
    public void onLoadMore() {
        viewModel.incrementStartpage();
        getNews(viewModel.getStartPage());
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
                        viewModel.setTotalItemCounts(getDBCurrentListSize());
                    }
                    adapter.setLoaded();
                    break;
                case ERROR:
                    break;
                case LOADING:
                    break;
            }
        });
    }

    public void openDetails(String id, View view) {
        Intent intent = new Intent(this, NewsDetailsActivity.class);
        intent.putExtra(USER_ID, id);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, getString(R.string.news_thumb_transition));
        startActivity(intent, options.toBundle());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UpdateDbEvent event) {
        viewModel.setStartPage(1);
        viewModel.setTotalItemCounts(viewModel.currentDBListSize());
    }
}
