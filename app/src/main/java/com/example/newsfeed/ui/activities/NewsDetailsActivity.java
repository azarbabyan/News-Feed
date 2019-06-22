package com.example.newsfeed.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.newsfeed.R;
import com.example.newsfeed.viewmodels.DetailsViewModel;

public class NewsDetailsActivity extends AppCompatActivity {
    private DetailsViewModel viewModel;
    private TextView category;
    private TextView title;
    private ImageView thumb;
    private String resultId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        bindViews();
        resultId = getIntent().getStringExtra("id");
        viewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        viewModel.getResultById(resultId).observe(this,result -> {
            if (result!=null){
                category.setText(result.getSectionName());
                title.setText(result.getWebTitle());
                if (result.getFields()!=null && result.getFields().getThumbnail()!=null){
                    Glide.with(this)
                            .load(result.getFields().getThumbnail())
                            .apply(new RequestOptions()
                                    .placeholder(R.drawable.placeholder))
                            .into(thumb);
                }else {
                    thumb.setImageResource(R.drawable.content_not_available);
                }

            }
        });

    }
    private void bindViews(){
        category = findViewById(R.id.category);
        title = findViewById(R.id.title);
        thumb = findViewById(R.id.thumb);
    }
}
