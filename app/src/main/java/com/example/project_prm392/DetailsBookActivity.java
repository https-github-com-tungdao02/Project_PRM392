package com.example.project_prm392;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.project_prm392.entity.Book;
import com.example.project_prm392.entity.User;

public class DetailsBookActivity extends AppCompatActivity {
    ImageView imv;
    TextView tv_View,tv_Name,tv_Author,tv_Like,tv_Des ;
    Button btnRead,btnComment;

    Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_book);
        imv=findViewById(R.id.imageView3);
        tv_View=findViewById(R.id.textView5);
        tv_Name=findViewById(R.id.textView4);
        tv_Author=findViewById(R.id.textView6);
        tv_Like=findViewById(R.id.tvLike);
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
                Intent intent = new Intent(DetailsBookActivity.this, MainActivity.class);
                intent.putExtra("fragmentToOpen", "read");
                startActivity(intent);
            }
        });

    }
}