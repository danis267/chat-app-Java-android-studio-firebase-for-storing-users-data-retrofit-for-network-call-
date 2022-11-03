package com.example.whatsapp;

import android.annotation.SuppressLint;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.whatsapp.Adapters.chatAdapter;
import com.example.whatsapp.Models.Messages;
import com.example.whatsapp.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

public class chatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private ArrayList<Messages> messages;
    private chatAdapter chatAdapter;
    private FirebaseDatabase database;
    private Messages messageModel;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//        DatabaseReference scoresRef = FirebaseDatabase.getInstance().getReference("scores");
//        scoresRef.keepSynced(true);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        Objects.requireNonNull(getSupportActionBar()).hide();


        binding.cback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        String F = getIntent().getStringExtra("fName");
        String L = getIntent().getStringExtra("lName");
        String sId = auth.getUid();
        String Rid = getIntent().getStringExtra("id");
        String profile = getIntent().getStringExtra("pic");
        binding.name.setText(String.format(
                "%s %s",
                 F,
                 L
        ));
        Picasso.get().load(profile).placeholder(R.drawable.user).into(binding.profile);

        final String senderRoom = sId + Rid;
        final String receiverRoom = Rid + sId;

//        preferenceManager.putString("sId", senderRoom);
//        preferenceManager.putString("Rid", receiverRoom);

        messages = new ArrayList<>();
        chatAdapter = new chatAdapter(messages, this, senderRoom, receiverRoom);
        binding.rid.setAdapter(chatAdapter);
        binding.rid.setLayoutManager(new LinearLayoutManager(this));



        database.getReference().child("Chats")
                .child(senderRoom)
                .child("messages")
                .addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                          messages.clear();
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            Messages messageModel = dataSnapshot.getValue(Messages.class);
                            assert messageModel != null;
                            messageModel.setMessageId(dataSnapshot.getKey());
                             messages.add(messageModel);
                             //scrollDown();
                        }
                        if (messages.size() > 0) {
                            scrollDown();
                            chatAdapter.notifyDataSetChanged();
                            //scrollDown();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        binding.send.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                String message = binding.etext.getText().toString().trim();
//                Calendar calendar = Calendar.getInstance();
                Calendar calendar = Calendar.getInstance();

                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d h:mm a");
                if (!message.trim().isEmpty()) {

                    messageModel = new Messages();
                    messageModel.setuId(sId);
                    messageModel.setMessage(message);
                    messageModel.setTimestamp2(simpleDateFormat.format(calendar.getTime()));
                    binding.etext.setText("");

                    database.getReference().child("Chats")
                            .child(senderRoom)
                            .child("messages")
                            .push()
                            .setValue(messageModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            database.getReference().child("Chats")
                                    .child(receiverRoom)
                                    .child("messages")
                                    .push()
                                    .setValue(messageModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            });
                        }
                    });

                }
                else {
                    Toast.makeText(chatActivity.this, "Enter Message", Toast.LENGTH_SHORT).show();
                }

            }

        });


    }
    private void scrollDown(){
        binding.scrollView.smoothScrollTo(0, binding.rid.getBottom());
    }


}