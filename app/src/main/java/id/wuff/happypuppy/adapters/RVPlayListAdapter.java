package id.wuff.happypuppy.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.wuff.happypuppy.R;
import id.wuff.happypuppy.model.Play;

public class RVPlayListAdapter extends RecyclerView.Adapter<RVPlayListAdapter.ViewHolder> {

    private ArrayList<Play> plays;

    public RVPlayListAdapter(ArrayList<Play> plays) {
        this.plays = plays;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_play_list, parent, false);
        return new RVPlayListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvPlay.setText("Today at "+ plays.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return plays.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvPlay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPlay = itemView.findViewById(R.id.tv_item_play);
        }
    }

}
