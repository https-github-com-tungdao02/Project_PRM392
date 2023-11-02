package com.example.project_prm392;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import com.example.project_prm392.entity.Community;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import java.util.Random;
import java.util.List;
import java.util.UUID;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder> {

    private List<Community> communityList;

    public CommunityAdapter(List<Community> communityList) {
        this.communityList = communityList;
    }

    public static class CommunityViewHolder extends RecyclerView.ViewHolder {
        public TextView descriptionTextView;
        public TextView dateTextView;
        public TextView likeData;
        public ImageView likebtn;


        public CommunityViewHolder(View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.communityDescription);
            dateTextView = itemView.findViewById(R.id.communityDate);
            likeData = itemView.findViewById(R.id.likedata);
            likebtn = itemView.findViewById(R.id.likeBtn);
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
        holder.descriptionTextView.setText("Description: " + community.getDescription());
        holder.dateTextView.setText("Date: " + community.getDate());
        holder.likeData.setText(community.getLike()+"");
        holder.likebtn.setTag(community.firebaseId);
        holder.likebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemId = v.getTag().toString();
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = db.getReference("Communities");
                DatabaseReference itemRef = databaseReference.child(itemId);
                itemRef.child("like").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int currentLikes = dataSnapshot.getValue(Integer.class);
                        itemRef.child("like").setValue(currentLikes + 1);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return communityList.size();
    }
}


