package com.example.whatsapp.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.whatsapp.Adapters.userAdapter;
import com.example.whatsapp.Models.Users;
import com.example.whatsapp.databinding.FragmentChatsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class chats extends Fragment {

    userAdapter userAdapter;

    public chats() {
        // Required empty public constructor
    }


    FragmentChatsBinding binding;
    ArrayList<Users> list = new ArrayList<>();
    FirebaseDatabase database;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatsBinding.inflate(inflater, container, false);
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//        DatabaseReference scoresRef = FirebaseDatabase.getInstance().getReference("scores");
//        scoresRef.keepSynced(true);
        database = FirebaseDatabase.getInstance();
        binding.swipe.setOnRefreshListener(this::getUsers);
        userAdapter = new userAdapter(list, getContext());
        binding.chatRecyclerview.setAdapter(userAdapter);
        binding.chatRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        getUsers();

        return binding.getRoot();
    }
    public void getUsers() {
        binding.swipe.setRefreshing(true);
        database.getReference().child("Users")
                .addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                binding.swipe.setRefreshing(false);

                list.clear();
                String userId = FirebaseAuth.getInstance().getUid();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {

                    Users users = dataSnapshot.getValue(Users.class);
                    assert users != null;
                    assert userId != null;
                    if (userId.equals(dataSnapshot.getKey())) {
                        continue;
                    }
                    String key = dataSnapshot.getKey();
                    users.setUserId(key);

                    list.add(users);

                }
                if (list.size() > 0) {
                    userAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}