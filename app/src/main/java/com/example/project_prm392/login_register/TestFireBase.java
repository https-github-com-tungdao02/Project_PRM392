package com.example.project_prm392.login_register;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import com.example.project_prm392.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TestFireBase extends AppCompatActivity {
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tham chiếu đến Firebase Realtime Database
        reference = FirebaseDatabase.getInstance().getReference("Users");

        // Đăng ký lắng nghe sự kiện
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Lặp qua tất cả các nút con trong dataSnapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Lấy giá trị của thuộc tính name
                    String name = snapshot.child("userName").getValue(String.class);

                    // Hiển thị tên trong logcat
                    Log.d("User", "Name: " + name);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình đọc dữ liệu
                Log.e("Firebase", "Đã xảy ra lỗi: " + databaseError.getMessage());
            }
        });
    }
}