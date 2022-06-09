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

import java.util.List;

public class user_disply_adapter extends RecyclerView.Adapter<user_disply_adapter.ViewHolder> {


    private Context context;
    private List<User> users;
    private boolean is_avail ;
    public user_disply_adapter(Context context, List<User> users, boolean is_avail){
        this.context = context;
        this.users = users;
        this.is_avail = is_avail;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(context).inflate(R.layout.user_item_view,parent,false);

        return new user_disply_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.username.setText(user.getUsername());

        if(user.getImageUrl().equals("default")){
            holder.profile_dp.setImageResource(R.mipmap.ic_launcher);

        }
        holder.last_message.setText(user.getAbout_one_line());

        if(is_avail){
            if(user.getStatus().equals("online")){
                holder.status_on.setVisibility(View.VISIBLE);
                holder.status_off.setVisibility(View.GONE);

            }
            else{
                holder.status_on.setVisibility(View.GONE);
                holder.status_off.setVisibility(View.VISIBLE);
            }
        }
        else{
            holder.status_on.setVisibility(View.GONE);
            holder.status_off.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Message_activity.class);
                i.putExtra("Friend Id", user.getUserid());
                context.startActivity(i);
            }
        });
        
    }

    @Override
    public int getItemCount() {

        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView profile_dp;
        public TextView last_message;
        public ImageView status_on;
        public ImageView status_off;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.name_of_user);
            profile_dp = itemView.findViewById(R.id.profile_pic);
            last_message = itemView.findViewById(R.id.last_message);
            status_on = itemView.findViewById(R.id.status_on);
            status_off = itemView.findViewById(R.id.status_off);



        }
    }







}
