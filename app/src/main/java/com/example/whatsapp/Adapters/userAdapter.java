package com.example.whatsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.Models.Users;
import com.example.whatsapp.R;
import com.example.whatsapp.chatActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class userAdapter extends RecyclerView.Adapter<userAdapter.viewholder>{

    ArrayList<Users> list;
    Context context;

    public userAdapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_user_list, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Users users = list.get(position);
        Picasso.get().load(users.getProfilePic()).placeholder(R.drawable.user).into(holder.image);
        holder.userName.setText(String.format(
                "%s %s",
                users.getFirstName(),
                users.getLastName())
        );
        //holder.lastMessage.setText(users.getLastMessage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, chatActivity.class);
                intent.putExtra("fName", users.getFirstName());
                intent.putExtra("lName", users.getLastName());
                intent.putExtra("pic", users.getProfilePic());
                intent.putExtra("id", users.getUserId());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView userName, lastMessage;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.textchar1);
            userName = itemView.findViewById(R.id.username);
            lastMessage = itemView.findViewById(R.id.lastmsg);
        }
    }

}
