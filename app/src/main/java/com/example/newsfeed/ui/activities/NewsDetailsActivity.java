package com.example.newsfeed.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.newsfeed.R;
import com.example.newsfeed.data.database.PinedNews;
import com.example.newsfeed.network.data.Result;
import com.example.newsfeed.viewmodels.DetailsViewModel;

public class NewsDetailsActivity extends AppCompatActivity {
    private DetailsViewModel viewModel;
    private TextView category;
    private TextView title;
    private ImageView thumb;
    private Toolbar toolbar;
    private Button save;
    private ImageView back;
    private String resultId;
    private Result result;
    private Button visit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        bindViews();
        setSupportActionBar(toolbar);
        resultId = getIntent().getStringExtra("id");
        viewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        viewModel.getResultById(resultId).observe(this, result -> {
            if (result != null) {
                NewsDetailsActivity.this.result = result;
                category.setText(result.getSectionName());
                title.setText(result.getWebTitle());
                if (result.getFields() != null && result.getFields().getThumbnail() != null) {
                    Glide.with(this)
                            .load(result.getFields().getThumbnail())
                            .apply(new RequestOptions()
                                    .placeholder(R.drawable.placeholder))
                            .into(thumb);
                } else {
                    thumb.setImageResource(R.drawable.content_not_available);
                }

            }
        });
        save.setOnClickListener(v -> {
            if (result != null) {
                PinedNews pinedNews = new PinedNews(result);
                viewModel.insertPinnedNews(pinedNews);
            }
        });
        back.setOnClickListener(v -> {
            NewsDetailsActivity.this.onBackPressed();
        });
        visit.setOnClickListener(v -> {
            if (result != null) {
                String url = result.getWebUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }

    private void bindViews() {
        toolbar = findViewById(R.id.toolbar);
        category = toolbar.findViewById(R.id.toolbar_title);
        title = findViewById(R.id.title);
        thumb = findViewById(R.id.thumb);
        visit = findViewById(R.id.visit);
        save = toolbar.findViewById(R.id.save);
        back = toolbar.findViewById(R.id.back_arrow);
    }
}
