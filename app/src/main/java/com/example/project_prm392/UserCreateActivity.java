package com.example.project_prm392;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.UUID;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project_prm392.entity.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserCreateActivity extends AppCompatActivity {

    EditText idEmail, idUserName, idPassword, idPhone , idAddress, idImage;
    Button buttonAdd, buttonCancel;
    Spinner spinnerGender, spinnerRole;

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_create);

        idEmail = findViewById(R.id.idEmail);
        idUserName = findViewById(R.id.idUserName);
        idPassword =findViewById(R.id.idPassword);
        idPhone = findViewById(R.id.idPhone);
        idAddress = findViewById(R.id.idAddress);
        idImage = findViewById(R.id.idImage);


        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerRole = findViewById(R.id.spinnerRole);

        ArrayAdapter<CharSequence> adapterRole = ArrayAdapter.createFromResource(this, R.array.roles_array, android.R.layout.simple_spinner_item);
        adapterRole.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapterRole);

        ArrayAdapter<CharSequence> adapterGender = ArrayAdapter.createFromResource(this, R.array.genders_array, android.R.layout.simple_spinner_item);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapterGender);

        buttonAdd = findViewById(R.id.buttonAdd);
        buttonCancel = findViewById(R.id.buttonCancel);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UUID uuid = UUID.randomUUID();
                String id = uuid.toString();
                String email = idEmail.getText().toString();
                String user = idUserName.getText().toString();
                String password = idPassword.getText().toString();
                String phone = idPhone.getText().toString();
                String address = idAddress.getText().toString();
                String image = idImage.getText().toString();

                if (email.isEmpty() || user.isEmpty() || password.isEmpty() || phone.isEmpty() || address.isEmpty() || image.isEmpty()) {
                    Toast.makeText(UserCreateActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    int role = (int) spinnerRole.getSelectedItemId();
                    String gender = spinnerGender.getSelectedItem().toString();

                    database.child("user").push().setValue(new User(id, user,password,role,phone,address,image,gender, email)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(UserCreateActivity.this, "Data success", Toast.LENGTH_SHORT);
                            startActivity(new Intent(UserCreateActivity.this, MainActivityAdmin.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UserCreateActivity.this, "Data Failure", Toast.LENGTH_SHORT);
                        }
                    });
                }
            }
        });
    }
}