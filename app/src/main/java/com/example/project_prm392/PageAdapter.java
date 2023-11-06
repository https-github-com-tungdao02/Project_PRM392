package com.example.project_prm392;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_prm392.entity.Book;
import com.example.project_prm392.entity.Community;
import com.example.project_prm392.entity.Page;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PageAdapter extends RecyclerView.Adapter<PageAdapter.PageViewHolder>{
    private List<Page> PageList;
    private TextView pageContext;

    private int bookKey;

    public PageAdapter(List<Page> PageList, TextView pageContext,int bookKey) {
        this.PageList = PageList;
        this.pageContext = pageContext;
        this.bookKey = bookKey;
    }

    public static class PageViewHolder extends RecyclerView.ViewHolder {
        public Button chapter;


        public PageViewHolder(View itemView) {
            super(itemView);
            chapter = itemView.findViewById(R.id.chapter);
        }
    }

    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_item, parent, false);
        return new PageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PageAdapter.PageViewHolder holder, int position) {
        Page p = PageList.get(position);
        holder.chapter.setText(""+p.getNumber());
        holder.chapter.setTag(p.getNumber());
        holder.chapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference ref = db.getReference("page");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                            Book book = childSnapshot.child("book").getValue(Book.class);
                            if(book.getId() == bookKey && childSnapshot.child("number").getValue(int.class) == holder.chapter.getTag()){
                                String content = childSnapshot.child("content").getValue(String.class);
                                pageContext.setText(content);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return PageList.size();
    }
}
