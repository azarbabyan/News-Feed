package com.example.newsfeed.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.newsfeed.R;
import com.example.newsfeed.data.database.PinedNews;
import com.example.newsfeed.ui.activities.MainActivity;

public class PinnedNewsAdapter extends PagedListAdapter<PinedNews, PinnedNewsAdapter.ViewHolder> {


    public PinnedNewsAdapter() {
        super(DIFF_CALLBACK);
    }


    @NonNull
    @Override
    public PinnedNewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.pinned_feed_row_layout, viewGroup, false);
        return new PinnedNewsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PinnedNewsAdapter.ViewHolder viewHolder, int i) {
        PinedNews result = getItem(i);
        if (result != null) {
            viewHolder.bind(result);
        } else {
            viewHolder.clear();
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView thumb;
        TextView category;
        PinedNews result;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_title);
            thumb = itemView.findViewById(R.id.news_thumb);
            category = itemView.findViewById(R.id.news_category);
            thumb.setOnClickListener(this);
        }

        void bind(PinedNews result) {
            this.result = result;
            title.setText(result.getWebTitle());
            category.setText(result.getSectionName());
            if (result.getFields() != null && result.getFields().getThumbnail() != null) {
                Glide.with(thumb.getContext())
                        .load(result.getFields().getThumbnail())
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.placeholder))
                        .into(thumb);
            } else {
                thumb.setImageResource(R.drawable.content_not_available);
            }
        }

        void clear() {
            title.setText(null);
            category.setText(null);
            thumb.setImageResource(R.drawable.content_not_available);
        }

        @Override
        public void onClick(View v) {
            if (itemView.getContext() instanceof MainActivity) {
                ((MainActivity) itemView.getContext()).openDetails(result.getId(), v);
            }
        }
    }

    private static DiffUtil.ItemCallback<PinedNews> DIFF_CALLBACK = new DiffUtil.ItemCallback<PinedNews>() {
        @Override
        public boolean areItemsTheSame(@NonNull PinedNews oldItem, @NonNull PinedNews newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull PinedNews oldItem, @NonNull PinedNews newItem) {
            return oldItem.equals(newItem);
        }
    };
}

