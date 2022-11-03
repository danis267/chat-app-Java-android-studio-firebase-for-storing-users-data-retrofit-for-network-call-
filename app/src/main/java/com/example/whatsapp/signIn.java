package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.whatsapp.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class signIn extends AppCompatActivity {

    ActivitySignInBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
     //   FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//        DatabaseReference scoresRef = FirebaseDatabase.getInstance().getReference("scores");
//        scoresRef.keepSynced(true);
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser()!= null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        Objects.requireNonNull(getSupportActionBar()).hide();
        binding.msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), signup.class);
                startActivity(intent);
            }
        });
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.inputemail.getText().toString().trim().isEmpty()) {
                    Toast.makeText(signIn.this, "Enter Email Address", Toast.LENGTH_SHORT).show();
                } else if (binding.inputpass.getText().toString().trim().isEmpty()) {
                    Toast.makeText(signIn.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else if(!Patterns.EMAIL_ADDRESS.matcher(binding.inputemail.getText().toString()).matches()) {
                    Toast.makeText(signIn.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                } else {
                    signIN();
                }

            }
        });
    }
    private void signIN() {
        binding.button.setVisibility(View.INVISIBLE);
        binding.btn.setVisibility(View.VISIBLE);

        String E = binding.inputemail.getText().toString();
        String P = binding.inputpass.getText().toString();

        auth.signInWithEmailAndPassword(E, P).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else {
                    binding.btn.setVisibility(View.INVISIBLE);
                    binding.button.setVisibility(View.VISIBLE);
                    Toast.makeText(signIn.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}