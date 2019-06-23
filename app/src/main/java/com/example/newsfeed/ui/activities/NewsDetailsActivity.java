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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.newsfeed.R;
import com.example.newsfeed.data.database.PinedNews;
import com.example.newsfeed.network.data.Result;
import com.example.newsfeed.viewmodels.DetailsViewModel;

import static com.example.newsfeed.utils.AppConstants.USER_ID;

public class NewsDetailsActivity extends AppCompatActivity {
    private DetailsViewModel viewModel;
    private TextView category;
    private TextView title;
    private ImageView thumb;
    private Toolbar toolbar;
    private Button save;
    private ImageView back;
    private Result result;
    private Button visit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        bindViews();
        setSupportActionBar(toolbar);
        viewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        viewModel.setResultId(getIntent().getStringExtra(USER_ID));
        viewModel.getResultById(viewModel.getResultId()).observe(this, result -> {
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
        save.setClickable(false);
        viewModel.setPined(viewModel.isPined(viewModel.getResultId()));
        if (!viewModel.isPined()) {
            save.setText(R.string.save);
        } else {
            save.setText(R.string.delete);
        }
        save.setClickable(true);
        save.setOnClickListener(v -> {
            if (result != null) {
                PinedNews pinedNews = new PinedNews(result);
                if (!viewModel.isPined()) {
                    boolean isInserted = viewModel.insertPinnedNews(pinedNews);
                    if (isInserted) {
                        viewModel.setPined(true);
                        save.setText(R.string.delete);
                        Toast.makeText(this, "News Pined", Toast.LENGTH_SHORT).show();
                    } else {
                        viewModel.setPined(false);
                        save.setText(R.string.save);
                        Toast.makeText(this, "News Pined Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    viewModel.deletePinnedNews(pinedNews);
                    save.setText(R.string.save);
                    Toast.makeText(this, "News Deleted", Toast.LENGTH_SHORT).show();
                    viewModel.setPined(false);
                }
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
