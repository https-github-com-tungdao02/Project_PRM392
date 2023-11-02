package com.example.project_prm392.login_register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_prm392.MainActivity;
import com.example.project_prm392.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class Home_Login extends AppCompatActivity {
    private TextView login, register;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    Button googleBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_login_activity);

        googleBtn = findViewById(R.id.button2);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            navigateToMainActivity();
        }

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        login = findViewById(R.id.textView);
        register = findViewById(R.id.textView2);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_Method();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register_Method();
            }
        });
    }

    private void login_Method() {
        Intent i = new Intent(Home_Login.this, LoginActivity.class);
        startActivity(i);
    }

    private void register_Method() {
        Intent i = new Intent(Home_Login.this, RegisterActivity.class);
        startActivity(i);
    }

    private void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    navigateToMainActivity();
                }
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(Home_Login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}