package com.example.chat_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {


    private static final int FLAG_RIGHT = 0;
    private static final  int FLAG_LEFT  = 1;



    private Context context;
    private List<Chat> chat_room;
    private String ImageUrl;

    FirebaseUser firebaseUser;

    public MessageAdapter(Context context, List<Chat> chat_room, String ImageUrl){
        this.context = context;


        this.chat_room = chat_room;

        this.ImageUrl = ImageUrl;
    }


    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        if(viewType == FLAG_RIGHT){
            view = LayoutInflater.from(context).inflate(R.layout.chat_right, parent, false);
        }
        else{
            view = LayoutInflater.from(context).inflate(R.layout.chat_left, parent, false);
        }
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Chat chat = chat_room.get(position);

        holder.show_msg.setText(chat.getMessage());
        if(ImageUrl.equals("default")){
            holder.profile.setImageResource(R.mipmap.ic_launcher_round);
        }

    }

    @Override
    public int getItemCount() {
        return chat_room.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView show_msg;
        public ImageView profile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            show_msg = itemView.findViewById(R.id.show_msg);
            profile = itemView.findViewById(R.id.chat_recivier_dp);




        }
    }

    @Override
    public int getItemViewType(int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(chat_room.get(position).getSender().equals(firebaseUser.getUid())){
            return FLAG_RIGHT;
        }
        else
            return FLAG_LEFT;
    }
}

