package com.example.whatsapp.Adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.Models.Messages;
import com.example.whatsapp.R;
import com.example.whatsapp.databinding.DeleteDialogBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class chatAdapter extends RecyclerView.Adapter{

    ArrayList<Messages> messages;
    FirebaseDatabase database;
    String senderRoom;
    String receiverRoom;
    Context context;

    int SENDER = 1;
    int RECEIVER = 2;

    public chatAdapter(ArrayList<Messages> messages, Context context, String senderRoom, String receiverRoom) {
        this.messages = messages;
        this.context = context;
        this.senderRoom = senderRoom;
        this.receiverRoom = receiverRoom;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == SENDER) {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender, parent, false);
            return new vieholderSender(view);

        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_receiver, parent, false);
            return new vieholderReceiver(view);
        }

    }

    @Override
    public int getItemViewType(int position) {

        if (messages.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())) {
            return SENDER;
        } else {
            return RECEIVER;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        database = FirebaseDatabase.getInstance();
        Messages msg = messages.get(position);

       if (holder.getClass() == vieholderSender.class) {
           ((vieholderSender)holder).smsg.setText(msg.getMessage());
           ((vieholderSender)holder).stime.setText(msg.getTimestamp2());
           ((vieholderSender)holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
               @Override
               public boolean onLongClick(View v) {

                   View view = LayoutInflater.from(context).inflate(R.layout.delete_dialog, null);
                   DeleteDialogBinding binding = DeleteDialogBinding.bind(view);
                   AlertDialog dialog = new AlertDialog.Builder(context)
                           .setTitle("Delete Message")
                           .setView(binding.getRoot())
                           .create();

                   binding.everyone.setOnClickListener(new View.OnClickListener() {
                       @SuppressLint("NotifyDataSetChanged")
                       @Override
                       public void onClick(View v) {
                           msg.setMessage("This message is removed.");
                           msg.setTimestamp2(null);
                           database.getReference().child("Chats").child(senderRoom)
                                   .child("messages")
                                   .child(msg.getMessageId()).setValue(msg);

                           database.getReference().child("Chats").child(receiverRoom)
                                   .child("messages")
                                   .child(msg.getMessageId()).setValue(msg);
                           dialog.dismiss();
                           notifyDataSetChanged();
                       }
                   });
                   binding.delete.setOnClickListener(new View.OnClickListener() {
                       @SuppressLint("NotifyDataSetChanged")
                       @Override
                       public void onClick(View v) {
                           database.getReference().child("Chats").child(senderRoom)
                                   .child("messages")
                                   .child(msg.getMessageId()).setValue(null);
                           dialog.dismiss();
                           notifyDataSetChanged();
                       }
                   });
                   binding.cancel.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           dialog.dismiss();
                       }
                   });
                   dialog.show();

                   return false;
               }
           });
       } else {
           ((vieholderReceiver)holder).rmsg.setText(msg.getMessage());
           ((vieholderReceiver)holder).rtime.setText(msg.getTimestamp2());
           ((vieholderReceiver)holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
               @Override
               public boolean onLongClick(View v) {

                   View view = LayoutInflater.from(context).inflate(R.layout.delete_dialog, null);
                   DeleteDialogBinding binding = DeleteDialogBinding.bind(view);
                   AlertDialog dialog = new AlertDialog.Builder(context)
                           .setTitle("Delete Message")
                           .setView(binding.getRoot())
                           .create();

//                   binding.everyone.setOnClickListener(new View.OnClickListener() {
//                       @Override
//                       public void onClick(View v) {
//                           msg.setMessage("This message is removed.");
//                           database.getReference().child("Chats").child(senderRoom)
//                                   .child("messages")
//                                   .child(msg.getMessageId()).setValue(msg);
//
//                           database.getReference().child("Chats").child(receiverRoom)
//                                   .child("messages")
//                                   .child(msg.getMessageId()).setValue(msg);
//                           dialog.dismiss();
//                       }
//                   });
                   binding.delete.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           msg.setMessage("This message is removed by you");
                           msg.setTimestamp2(null);
                           database.getReference().child("Chats").child(senderRoom)
                                   .child("messages")
                                   .child(msg.getMessageId()).setValue(msg);
                           dialog.dismiss();
                       }
                   });
                   binding.cancel.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           dialog.dismiss();
                       }
                   });
                   dialog.show();

                   return false;
               }
           });
       }
    }



    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class vieholderReceiver extends RecyclerView.ViewHolder {

        TextView rmsg, rtime;

        public vieholderReceiver(@NonNull View itemView) {
            super(itemView);
            rmsg = itemView.findViewById(R.id.rtext);
            rtime = itemView.findViewById(R.id.rtime);

        }
    }
    public static class vieholderSender extends RecyclerView.ViewHolder {

        TextView smsg, stime;

        public vieholderSender(@NonNull View itemView) {
            super(itemView);
            smsg = itemView.findViewById(R.id.sid);
            stime = itemView.findViewById(R.id.stime);

        }
    }
}
