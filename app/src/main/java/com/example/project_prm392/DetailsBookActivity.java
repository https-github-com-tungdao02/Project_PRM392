package com.example.project_prm392;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.project_prm392.entity.Book;
import com.example.project_prm392.entity.History;
import com.example.project_prm392.entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.UUID;

public class DetailsBookActivity extends AppCompatActivity {
    ImageView imv;
    TextView tv_View,tv_Name,tv_Author,tv_Like,tv_Des ;
    Button btnRead,btnComment;
    Book book;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_book);
        imv=findViewById(R.id.imageView3);
        tv_View=findViewById(R.id.textView5);
        tv_Name=findViewById(R.id.textView4);
        tv_Author=findViewById(R.id.textView6);
        tv_Like=findViewById(R.id.tvLike);
        tv_Des=findViewById(R.id.tv_Des_detail);
        btnRead=findViewById(R.id.button5);
        btnComment=findViewById(R.id.comment);
        Intent intent = getIntent();
        if(intent!=null){
             book = (Book) intent.getSerializableExtra("book");

            if(book!=null){
                Glide.with(this).load(book.getImage()).into(imv);
                tv_View.setText(book.getViews()+"");
                tv_Like.setText(book.getLike()+"");
                tv_Name.setText(book.getName()+"");
                tv_Author.setText(book.getAuthor()+"");
               tv_Des.setText(book.getDescription()+"");
            }
        }

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("bookId", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("keyId", book.getId()+"");
                editor.apply();
                Intent intent = new Intent(DetailsBookActivity.this, MainActivity.class);
                intent.putExtra("fragmentToOpen", "community");
                startActivity(intent);

            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("bookId", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("keyId", book.getId()+"");
                editor.apply();
                SharedPreferences sharedPreferences1= getSharedPreferences("account",MODE_PRIVATE);
                editor= sharedPreferences1.edit();
                String username=sharedPreferences1.getString("username","");
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                reference.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            user = snapshot.getValue(User.class);
                        }
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("history");
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                boolean check =false;
                                for(DataSnapshot dataSnapshot :snapshot.getChildren()){
                                    History history= dataSnapshot.getValue(History.class);
                                    if(history.getBook().getId()==book.getId()&& history.getUser().getUser().equals(username)){
                                        check=true;
                                        history.setTime(new Date());
                                        reference.child(history.getId()).setValue(history);
                                        break;
                                    }
                                }
                                if(check==false){
                                    String historyId = UUID.randomUUID().toString();
                                    Date currentTime = new Date();
                                    History history = new History(historyId,currentTime,book,user);
                                    reference.child(history.getId()).setValue(history);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Intent intent = new Intent(DetailsBookActivity.this, MainActivity.class);
                intent.putExtra("fragmentToOpen", "read");
                startActivity(intent);
            }
        });

    }
}