package com.example.whatsapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsapp.Models.Users;
import com.example.whatsapp.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class signup extends AppCompatActivity {
    ActivitySignupBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        Objects.requireNonNull(getSupportActionBar()).hide();

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.msignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.buttonup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.inputname.getText().toString().trim().isEmpty()) {
                    Toast.makeText(signup.this, "Enter First Name", Toast.LENGTH_SHORT).show();
                } else if (binding.inputLast.getText().toString().trim().isEmpty()) {
                    Toast.makeText(signup.this, "Enter Last Name", Toast.LENGTH_SHORT).show();
                } else if (binding.inpupass.getText().toString().trim().isEmpty()) {
                    Toast.makeText(signup.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else if (binding.inpuemail.getText().toString().trim().isEmpty()) {
                    Toast.makeText(signup.this, "Enter Email Address", Toast.LENGTH_SHORT).show();
                } else if (binding.inputpass2.getText().toString().trim().isEmpty()) {
                    Toast.makeText(signup.this, "Confirm Your Password", Toast.LENGTH_SHORT).show();
                } else if (!binding.inpupass.getText().toString().equals(binding.inputpass2.getText().toString())) {
                    Toast.makeText(signup.this, "Enter Valid Password", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.inpuemail.getText().toString()).matches()) {
                    Toast.makeText(signup.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                } else {
                    signUp();
                }
            }
        });
    }
    private void signUp() {
        binding.buttonup.setVisibility(View.INVISIBLE);
        binding.pbar.setVisibility(View.VISIBLE);
        String e = binding.inpuemail.getText().toString();
        String p = binding.inpupass.getText().toString();
        auth.createUserWithEmailAndPassword(e, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if (task.isSuccessful()) {
                   Users users = new Users();
                   users.setFirstName(binding.inputname.getText().toString());
                   users.setLastName(binding.inputLast.getText().toString());
                   users.setEmail(binding.inpuemail.getText().toString());
                   users.setPassWord(binding.inpupass.getText().toString());
                   String id = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getUser()).getUid();

                   database.getReference().child("Users")
                           .child(id)
                           .setValue(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void aVoid) {

                           Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                           intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                           startActivity(intent);

                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {

                           binding.pbar.setVisibility(View.INVISIBLE);
                           binding.buttonup.setVisibility(View.VISIBLE);
                           Toast.makeText(signup.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                       }
                   });
               } else {
                   binding.pbar.setVisibility(View.INVISIBLE);
                   binding.buttonup.setVisibility(View.VISIBLE);
                   Toast.makeText(signup.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
               }
            }
        });
    }
}