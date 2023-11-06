package com.example.project_prm392;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_prm392.entity.Book;
import com.example.project_prm392.entity.Community;
import com.example.project_prm392.entity.Page;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.SharedPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class PageFragment extends DialogFragment  {

    public PageFragment(){

    }

    public PageFragment(TextView page, int bookKey){
        this.contentPage = page;
        this.bookKey = bookKey;
    }

    final List<Page> pageList = new ArrayList<>();
    TextView contentPage;
    public int bookKey = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.chapterRecyclerView);
        final PageAdapter adapter = new PageAdapter(pageList,contentPage,bookKey);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("page");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Book book = childSnapshot.child("book").getValue(Book.class);
                    if(book.getId() == bookKey){
                        String content = childSnapshot.child("content").getValue(String.class);
                        int number = childSnapshot.child("number").getValue(int.class);
                        int id = childSnapshot.child("id").getValue(int.class);
                        Page p = new Page(id,content,book,number);
                        pageList.add(p);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }


}