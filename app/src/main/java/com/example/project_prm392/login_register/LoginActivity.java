package com.example.project_prm392.login_register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_prm392.MainActivity;
import com.example.project_prm392.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import android.os.AsyncTask;
import android.widget.Toast;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

import java.util.Random;

public class LoginActivity extends AppCompatActivity {
    private EditText userName, passWord;
    private CheckBox rememberMe;
    TextView forgotPassword;
    private Button login;



    // private FirebaseAuth mAuthFirebase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        // mAuthFirebase = FirebaseAuth.getInstance();

        userName = findViewById(R.id.userName);
        passWord = findViewById(R.id.passWord);
        login = findViewById(R.id.login);
        forgotPassword = findViewById(R.id.forgotPassWord);
        rememberMe = findViewById(R.id.rememberMe);


        SharedPreferences shared_pref= getSharedPreferences("account",MODE_PRIVATE);
        String usn=shared_pref.getString("username","");
        String pass=shared_pref.getString("password","");
        boolean is_checked=shared_pref.getBoolean("is_saved",false);
        userName.setText(usn);
        passWord.setText(pass);
        rememberMe.setChecked(is_checked);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateUsername() | !validatePassword()) {
                } else {
                    checkUser();
                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.forgot_password_dialog, null);
                EditText emailBox = dialogView.findViewById(R.id.emailBox);
                EditText userNameBox = dialogView.findViewById(R.id.userNameBox);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialogView.findViewById(R.id.btnReset).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String userEmail = emailBox.getText().toString();
                        String userName1 = userNameBox.getText().toString();
                        if (TextUtils.isEmpty(userEmail) && !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
                            Toast.makeText(LoginActivity.this, "Enter your registered email id", Toast.LENGTH_SHORT).show();
                            return;
                        }
                                    resetPassword(userEmail,userName1);
                                    Toast.makeText(LoginActivity.this, "Check your email", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                    }
                });
                dialogView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                if (dialog.getWindow() != null){
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();
            }
        });

        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    SharedPreferences perferences = getSharedPreferences("account", MODE_PRIVATE);
                    SharedPreferences.Editor editor = perferences.edit();
                    editor.putString("username",userName.getText().toString());
                    editor.putString("password",passWord.getText().toString());
                    editor.putBoolean("is_saved",true);
                    editor.commit();
                }
                else {
                    SharedPreferences perferences = getSharedPreferences("account", MODE_PRIVATE);
                    SharedPreferences.Editor editor = perferences.edit();
                    editor.remove("username");
                    editor.remove("password");
                    editor.putBoolean("is_saved",false);
                    editor.commit();
                }
            }
        });
    }

        public Boolean validateUsername() {
            String val = userName.getText().toString();
            if (val.isEmpty()) {
                userName.setError("Username cannot be empty");
                return false;
            } else {
                userName.setError(null);
                return true;
            }
        }
        public Boolean validatePassword(){
            String val = passWord.getText().toString();
            if (val.isEmpty()) {
                passWord.setError("Password cannot be empty");
                return false;
            } else {
                passWord.setError(null);
                return true;
            }
        }
    public void checkUser(){
        String userUsername = userName.getText().toString().trim();
        String userPassword = passWord.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(""+userUsername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String password = snapshot.child("password").getValue(String.class);
                    // Username already exists
                    if(userPassword.equals(password)){
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "UserName or PassWord not true!!!!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "UserName or PassWord not true!!!!", Toast.LENGTH_SHORT).show();
                    };
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Error occurred while checking username
                Toast.makeText(LoginActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resetPassword(String userEmail, String userName) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String userKey = snapshot.getChildren().iterator().next().getKey();
                    DatabaseReference userRef = reference.child(userKey);
                    String newPassword = generateRandomPassword();
                    reference.child(userName).child("password").setValue(newPassword)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        sendPasswordResetEmail(userEmail, newPassword);
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Failed to reset password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendPasswordResetEmail(String userEmail, String newPassword) {
        final String username = "truongson1082002@gmail.com"; // Thay thế bằng địa chỉ email của bạn
        final String password = "pvuw pqik jnak uany"; // Thay thế bằng mật khẩu của bạn

        // Cấu hình thuộc tính cho phiên làm việc JavaMail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // Thay thế bằng máy chủ SMTP của bạn
        props.put("mail.smtp.port", "465"); // Thay thế bằng cổng SMTP của bạn

        // Tạo đối tượng Session cho phiên làm việc JavaMail
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        // Triển khai AsyncTask để gửi email trong nền
        AsyncTask<Void, Void, Boolean> sendEmailTask = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    // Tạo đối tượng MimeMessage
                    MimeMessage message = new MimeMessage(session);
                    // Đặt thông tin người gửi
                    message.setFrom(new InternetAddress(username));
                    // Đặt thông tin người nhận
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
                    // Đặt chủ đề email
                    message.setSubject("Reset Password");
                    // Đặt nội dung email
                    message.setText("Your new password: " + newPassword);

                    // Gửi email
                    Transport.send(message);

                    return true;
                } catch (MessagingException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (success) {
                    Toast.makeText(LoginActivity.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Failed to send password reset email", Toast.LENGTH_SHORT).show();
                }
            }
        };

        // Thực thi AsyncTask để gửi email
        sendEmailTask.execute();
    }

    private String generateRandomPassword() {
        // Tạo mật khẩu mới ngẫu nhiên
        // Bạn có thể sử dụng thư viện hoặc tạo mật khẩu ngẫu nhiên theo cách riêng của mình
        // Ví dụ: Tạo mật khẩu từ chuỗi ký tự ngẫu nhiên
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder newPassword = new StringBuilder();
        Random random = new Random();
        int length = 8; // Độ dài mật khẩu mới

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            newPassword.append(characters.charAt(index));
        }
        return newPassword.toString();
    }

    public void updateObject( String objectId, Object updatedObject) {
        FirebaseDatabase.getInstance().getReference("users").child(objectId).setValue(updatedObject);
    }



}
