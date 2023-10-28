package com.example.project_prm392;

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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentEditText.getText().toString();
                String itemId  = UUID.randomUUID().toString();
                int like = 0;
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                String dateTime = dateFormat.format(calendar.getTime());
                int book_id=2;
                int user_id =1;
                CommunityDataModel community = new CommunityDataModel(comment,dateTime,like,book_id,user_id);

                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = db.getReference("Communities");
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

        public int user_id;
        public int book_id;

        public CommunityDataModel (String description,String date, int like,int user_id,int book_id){
            this.description = description;
            this.date = date;
            this.like = like;
            this.user_id = user_id;
            this.book_id = book_id;
        }
    }
}