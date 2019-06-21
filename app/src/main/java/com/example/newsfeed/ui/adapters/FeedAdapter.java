package com.example.newsfeed.ui.adapters;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.newsfeed.R;
import com.example.newsfeed.network.data.Result;

import java.util.ArrayList;
import java.util.List;

public class FeedAdapter extends PagedListAdapter<Result,FeedAdapter.ViewHolder> {
    private List<Result> data = new ArrayList<>();

    public FeedAdapter() {
        super(DIFF_CALLBACK);
    }

    public void updateList(List<Result> results){
        data = results;
        //notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.feed_row_layout,viewGroup,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Result result = getItem(i);
        if (result != null) {
            viewHolder.bind(result);
        } else {
            // Null defines a placeholder item - PagedListAdapter automatically
            // invalidates this row when the actual object is loaded from the
            // database.
            viewHolder.clear();
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView thumb;
        TextView category;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_title);
            thumb = itemView.findViewById(R.id.news_thumb);
            category = itemView.findViewById(R.id.news_category);
        }

        void bind(Result result){
            title.setText(result.getWebTitle());
            category.setText(result.getSectionName());
            if (result.getFields()!=null && result.getFields().getThumbnail()!=null) {
                Glide.with(thumb.getContext())
                        .load(result.getFields().getThumbnail())
                        .into(thumb);
            }else {
                thumb.setImageResource(R.drawable.ic_launcher_background);
            }
        }
        void clear(){
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
