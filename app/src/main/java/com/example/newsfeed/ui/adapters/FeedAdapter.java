package com.example.newsfeed.ui.adapters;

import androidx.annotation.NonNull;
import androidx.core.content.res.TypedArrayUtils;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.newsfeed.R;
import com.example.newsfeed.listeners.OnloadMoreListener;
import com.example.newsfeed.network.data.Result;
import com.example.newsfeed.ui.activities.MainActivity;

import java.util.Arrays;
import java.util.Collections;


public class FeedAdapter extends PagedListAdapter<Result, FeedAdapter.ViewHolder> {

    private boolean loading;

    public FeedAdapter(RecyclerView recyclerView, OnloadMoreListener onloadMoreListener) {
        super(DIFF_CALLBACK);
        if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            final StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    checkIfOnLoadMoreNeeded(layoutManager, onloadMoreListener, recyclerView);
                }
            });
        }
    }

    private void checkIfOnLoadMoreNeeded(StaggeredGridLayoutManager layoutManager, OnloadMoreListener onloadMoreListener, RecyclerView recyclerView) {
        int itemCount = layoutManager.getItemCount();
        int[] into = new int[layoutManager.getSpanCount()];
        int[] lastVisibleItemPositions = layoutManager.findLastVisibleItemPositions(into);
        int max = lastVisibleItemPositions[0];
        for (int lastVisibleItemPosition : lastVisibleItemPositions) {
            if (lastVisibleItemPosition > max) {
                max = lastVisibleItemPosition;
            }
        }
        int visibleThreshold = 10;
        if (!loading && itemCount <= (max + visibleThreshold)) {
            int dbCurrentListSize = getDBCurrentListSize(recyclerView);
            int totalItemCounts = getTotalItemCounts(recyclerView);
            if (dbCurrentListSize != -1 && totalItemCounts != -1) {
                if (dbCurrentListSize > totalItemCounts - 1 && onloadMoreListener != null) {
                    onloadMoreListener.onLoadMore();
                }
                loading = true;
            }
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

    public void setLoaded() {
        loading = false;
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


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView thumb;
        TextView category;
        Result result;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_title);
            thumb = itemView.findViewById(R.id.news_thumb);
            category = itemView.findViewById(R.id.news_category);
            thumb.setOnClickListener(this);
        }

        void bind(Result result) {
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
