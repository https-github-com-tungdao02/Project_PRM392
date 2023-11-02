package com.example.project_prm392;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.project_prm392.entity.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivityAdmin extends AppCompatActivity {


    FloatingActionButton addNew;
    AdminAdapter adminAdapter;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    ArrayList<User> userList;
    RecyclerView rc_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        addNew = findViewById(R.id.btn_add);

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivityAdmin.this,UserCreateActivity.class));
            }
        });

        rc_user = findViewById(R.id.rc_user);
        RecyclerView.LayoutManager mLayout = new LinearLayoutManager(this);
        rc_user.setLayoutManager(mLayout);
        rc_user.setItemAnimator(new DefaultItemAnimator());

        userData();
    }

    private void userData() {
        database.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList = new ArrayList<>();
                for(DataSnapshot item : snapshot.getChildren()){
                    User user = item.getValue(User.class);
                    user.setId(item.getKey());
                    userList.add(user);
                }
                adminAdapter = new AdminAdapter(userList, MainActivityAdmin.this);
                rc_user.setAdapter(adminAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}