package com.example.newsfeed.ui.adapters;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.newsfeed.R;
import com.example.newsfeed.listeners.OnloadMoreListener;
import com.example.newsfeed.network.data.Result;
import com.example.newsfeed.ui.activities.MainActivity;


public class FeedAdapter extends PagedListAdapter<Result, FeedAdapter.ViewHolder> {

    private int visibleThreshold = 10;

    public FeedAdapter(RecyclerView recyclerView, OnloadMoreListener onloadMoreListener) {
        super(DIFF_CALLBACK);
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int itemCount = layoutManager.getItemCount();
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                    if (itemCount <= (lastVisibleItemPosition + visibleThreshold)) {
                        int dbCurrentListSize = getDBCurrentListSize(recyclerView);
                        int totalItemCounts = getTotalItemCounts(recyclerView);
                        if (dbCurrentListSize != -1 && totalItemCounts != -1) {
                            if (dbCurrentListSize > totalItemCounts - 1 && onloadMoreListener != null) {
                                onloadMoreListener.onLoadMore();
                            }
                        }
                    }
                }
            });
        }
    }

    private int getDBCurrentListSize(View view) {
        if (view.getContext() != null) {
            if (view.getContext() instanceof MainActivity) {
                return ((MainActivity) view.getContext()).getDBCurrentListSize();
            }
        }
        return -1;
    }

    private int getTotalItemCounts(View view) {
        if (view.getContext() != null) {
            if (view.getContext() instanceof MainActivity) {
                return ((MainActivity) view.getContext()).getTotalItemCounts();
            }
        }
        return -1;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.feed_row_layout, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Result result = getItem(i);
        if (result != null) {
            viewHolder.bind(result);
        } else {
            viewHolder.clear();
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView thumb;
        TextView category;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_title);
            thumb = itemView.findViewById(R.id.news_thumb);
            category = itemView.findViewById(R.id.news_category);
        }

        void bind(Result result) {
            title.setText(result.getWebTitle());
            category.setText(result.getSectionName());
            if (result.getFields() != null && result.getFields().getThumbnail() != null) {
                Glide.with(thumb.getContext())
                        .load(result.getFields().getThumbnail())
                        .into(thumb);
            } else {
                thumb.setImageResource(R.drawable.ic_launcher_background);
            }
        }

        void clear() {
            title.setText(null);
            category.setText(null);
            thumb.setImageDrawable(null);
        }
    }

    private static DiffUtil.ItemCallback<Result> DIFF_CALLBACK = new DiffUtil.ItemCallback<Result>() {
        @Override
        public boolean areItemsTheSame(@NonNull Result oldItem, @NonNull Result newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Result oldItem, @NonNull Result newItem) {
            return oldItem.equals(newItem);
        }
    };
}
