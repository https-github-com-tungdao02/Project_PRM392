package com.example.project_prm392;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_prm392.entity.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminHolder> {
    private List<User> userList;

    private OnClickDetails onClickDetails;


    private Activity activity;

    public void setFilteredList(List<User> filteredList){
        this.userList = filteredList;
        notifyDataSetChanged();
    }

    public AdminAdapter(List<User> userList, OnClickDetails onClickDetails) {
        this.userList = userList;
        this.onClickDetails = onClickDetails;
    }

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public AdminAdapter(List<User>userList, Activity activity){
        this.userList = userList;
        this.activity = activity;
    }

    public interface  OnClickDetails{
        void onClickDetails(User user);
    }
    @NonNull
    @Override
    public AdminHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.main_item_account, parent, false);

        return new AdminHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminHolder holder, int position) {
        final User data = userList.get(position);
        holder.tv_name.setText("Name: " + data.getUser());
        holder.tv_address.setText("Address: " + data.getAddress());
        holder.tv_phone.setText("Phone: " + data.getPhone());
        if(data.getImage() == null || data.getImage().isEmpty()){
            Glide.with(holder.itemView).load("https://png.pngtree.com/element_our/20200610/ourlarge/pngtree-default-avatar-image_2237213.jpg").into(holder.imageViewUser);
        }else{
            Glide.with(holder.itemView).load(userList.get(position).getImage()).into(holder.imageViewUser);
        }
        holder.btn_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDetails.onClickDetails(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class AdminHolder extends RecyclerView.ViewHolder {
        CircleImageView imageViewUser;

        TextView tv_name;
        TextView tv_phone;
        TextView tv_address;
        CardView user_card_view;

        Button btn_details;

        public AdminHolder(@NonNull View itemView) {
            super(itemView);
            imageViewUser = itemView.findViewById(R.id.imagee);
            tv_address = itemView.findViewById(R.id.addresstext);
            tv_name = itemView.findViewById(R.id.nametext);
            tv_phone = itemView.findViewById(R.id.phonetext);
            user_card_view = itemView.findViewById(R.id.userCard);
            btn_details = itemView.findViewById(R.id.btn_details);
        }
    }
}
