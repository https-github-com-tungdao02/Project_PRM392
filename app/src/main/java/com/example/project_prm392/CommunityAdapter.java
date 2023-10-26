package com.example.project_prm392;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import com.example.project_prm392.entity.Community;
import java.util.Random;
import java.util.List;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder> {

    private List<Community> communityList;

    public CommunityAdapter(List<Community> communityList) {
        this.communityList = communityList;
    }

    public static class CommunityViewHolder extends RecyclerView.ViewHolder {
        public TextView descriptionTextView;
        public TextView dateTextView;
        public TextView likeData;

        public CommunityViewHolder(View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.communityDescription);
            dateTextView = itemView.findViewById(R.id.communityDate);
            likeData = itemView.findViewById(R.id.likedata);
        }
    }

    @NonNull
    @Override
    public CommunityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_item, parent, false);
        return new CommunityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityViewHolder holder, int position) {
        Community community = communityList.get(position);

        Random rand = new Random();
        holder.descriptionTextView.setText("Description: " + community.getDescription());
        holder.dateTextView.setText("Date: " + community.getDate());
        holder.likeData.setText((rand.nextInt(80)+10)+"");
    }

    @Override
    public int getItemCount() {
        return communityList.size();
    }
}


