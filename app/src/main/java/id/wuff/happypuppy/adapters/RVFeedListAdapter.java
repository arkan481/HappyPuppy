package id.wuff.happypuppy.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.wuff.happypuppy.R;
import id.wuff.happypuppy.model.Feed;
import id.wuff.happypuppy.model.Play;

public class RVFeedListAdapter extends RecyclerView.Adapter<RVFeedListAdapter.ViewHolder> {

    private ArrayList<Feed> feeds;

    public RVFeedListAdapter(ArrayList<Feed> feeds) {
        this.feeds = feeds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed_list, parent, false);
        return new RVFeedListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvFeed.setText("Today at "+ feeds.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvFeed;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvFeed = itemView.findViewById(R.id.tv_item_feed);
        }
    }

}
