package com.example.project_prm392;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.fragment.app.DialogFragment;

import com.example.project_prm392.entity.Community;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.content.SharedPreferences;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class CommentFragment extends DialogFragment  {
    EditText commentEditText;
    Button saveButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        commentEditText = view.findViewById(R.id.commentEditText);
        saveButton = view.findViewById(R.id.saveButton);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("bookId", Context.MODE_PRIVATE);
        String bookKey = sharedPreferences.getString("keyId","");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("account", Context.MODE_PRIVATE);
                String userName = sharedPreferences.getString("username","");
                String comment = commentEditText.getText().toString();
                String itemId  = UUID.randomUUID().toString();
                int like = 0;
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                String dateTime = dateFormat.format(calendar.getTime());
                int book_id= Integer.parseInt(bookKey);
                CommunityDataModel community = new CommunityDataModel(comment,dateTime,like,userName, book_id);

                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = db.getReference("communities");
                databaseReference.child(itemId).setValue(community);
                dismiss();
            }
        });
        return view;
    }

    public class CommunityDataModel{
        public String description;
        public String date;

        public int like;

        public String user;
        public int book_id;

        public CommunityDataModel (String description,String date, int like,String user,int book_id){
            this.description = description;
            this.date = date;
            this.like = like;
            this.user = user;
            this.book_id = book_id;
        }
    }
}