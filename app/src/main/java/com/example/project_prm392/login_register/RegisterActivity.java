package com.example.project_prm392.login_register;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_prm392.MainActivity;
import com.example.project_prm392.R;
import com.example.project_prm392.entity.User;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {
    TextView r_userName, r_passWord, r_confirmPassWord, r_phone, r_email;
    Button r_register;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        r_userName = findViewById(R.id.R_UserName);
        r_passWord = findViewById(R.id.R_PassWord);
        r_confirmPassWord = findViewById(R.id.R_ConfirmPassWord);
        r_email = findViewById(R.id.R_Email);
        r_phone = findViewById(R.id.R_Phone);
        r_register = findViewById(R.id.R_Register);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        r_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName, passWord, confirmPassWord, email, phone;
                userName = r_userName.getText().toString().trim();
                passWord = r_passWord.getText().toString().trim();
                confirmPassWord = r_confirmPassWord.getText().toString().trim();
                email = r_email.getText().toString().trim();
                phone = r_phone.getText().toString().trim();

                if (!validateUsername() || !validatePassword() || !validateConfirmPassWord() || !validatePhone()) {
                    return;
                }
                if (!passWord.equals(confirmPassWord)) {
                    Toast.makeText(RegisterActivity.this, "ConfirmPassWord is wrong !!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                User user = new User();
                user.setUser(userName);
                user.setPassword(passWord);
                user.setId(UUID.randomUUID().toString());
                user.setEmail(email);
                user.setRole(2);
                user.setAddress(null);
                user.setGender(null);
                user.setImage(null);
                user.setPhone(phone);
                checkEmailExist(user);
            }
        });
    }

    public boolean validateUsername() {
        String val = r_userName.getText().toString().trim();
        String regex = "^[a-zA-Z0-9_]*$";
        if (val.isEmpty()) {
            r_userName.setError("Username cannot be empty");
            return false;
        } else if (!val.matches(regex)) {
            r_userName.setError("Username cannot contain special characters");
            return false;
        } else {
            r_userName.setError(null);
            return true;
        }
    }

    public boolean validatePassword() {
        String val = r_passWord.getText().toString().trim();
        if (val.isEmpty()) {
            r_passWord.setError("Password cannot be empty");
            return false;
        } else {
            r_passWord.setError(null);
            return true;
        }
    }

    public boolean validateConfirmPassWord() {
        String val = r_confirmPassWord.getText().toString().trim();
        if (val.isEmpty()) {
            r_confirmPassWord.setError("ConfirmPassWord cannot be empty");
            return false;
        } else {
            r_confirmPassWord.setError(null);
            return true;
        }
    }

    public boolean validatePhone() {
        String val = r_phone.getText().toString().trim();
        if (val.isEmpty()) {
            r_phone.setError("Phone cannot be empty");
            return false;
        } else {
            r_phone.setError(null);
            return true;
        }
    }

    private void checkUserNameExist(String userName, User user) {
        reference.child(userName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Username already exists
                    Toast.makeText(RegisterActivity.this, "The username already exists", Toast.LENGTH_SHORT).show();
                } else {
                    // Username is available
                    reference.child(userName).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RegisterActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkEmailExist(User user) {
        String email = user.getEmail();
        Query query = reference.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Email already exists
                    Toast.makeText(RegisterActivity.this, "The email already exists", Toast.LENGTH_SHORT).show();
                } else {
                    // Email is available
                    checkUserNameExist(user.getUser(), user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RegisterActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}