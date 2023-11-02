package com.example.project_prm392;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.project_prm392.entity.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.appcompat.widget.SearchView;


import java.util.ArrayList;
import java.util.List;

public class MainActivityAdmin extends AppCompatActivity {


    FloatingActionButton addNew;
    AdminAdapter adminAdapter;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    ArrayList<User> userList;
    RecyclerView rc_user;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        addNew = findViewById(R.id.btn_add);
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fileList(newText);
                return true;
            }
        });

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

    private void fileList(String newText) {
        List<User> filteredList = new ArrayList<>();
        for (User user : userList){
            if(user.getUser().toLowerCase().trim().contains(newText.toLowerCase())){
                filteredList.add(user);
            }
        }
        if(filteredList.isEmpty()){
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }else {
            adminAdapter.setFilteredList(filteredList);
        }
    }

    private void userData() {
        database.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList = new ArrayList<>();
                for(DataSnapshot item : snapshot.getChildren()){
                    User user = item.getValue(User.class);
                    user.setId(item.getKey());
                    userList.add(user);
                }
                adminAdapter = new AdminAdapter(userList, MainActivityAdmin.this);
                rc_user.setAdapter(new AdminAdapter(userList, new AdminAdapter.OnClickDetails() {
                    @Override
                    public void onClickDetails(User user) {
                        openDialogDetail(user);
                    }
                }));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openDialogDetail(User user) {
        Dialog dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.user_details_dialog);
        Window window= dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setCancelable(false);
        dialog.show();
    }
}