package com.example.chat_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class Home_Fragment extends Fragment {

    private View PrivateChatsView;


    RecyclerView recyclerView;


    user_disply_adapter user_disply_adapter;

      List<User> chat_list;


    FirebaseUser firebaseUser;
    DatabaseReference reference, reference1;

     List<String> userelist;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       PrivateChatsView =  inflater.inflate(R.layout.fragment_home_, container, false);


       recyclerView = PrivateChatsView.findViewById(R.id.home_chat);


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        reference = FirebaseDatabase.getInstance().getReference().child("Chats");

        userelist = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                userelist.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Chat chat = snapshot1.getValue(Chat.class);

                    if(chat.getSender().equals(firebaseUser.getUid())){
                        userelist.add(chat.getReceiver());

                    }
                    if(chat.getReceiver().equals(firebaseUser.getUid())){
                        userelist.add(chat.getSender());
                    }
                }

                read_chats();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });









       return PrivateChatsView;
    }

    private void read_chats() {
        chat_list = new ArrayList<>();

        reference1 = FirebaseDatabase.getInstance().getReference("Registered users");

        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chat_list.clear();

                for( DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);

                    for(String id :  userelist){
                        if(user.getUserid().equals(id)){
                            if(!chat_list.isEmpty()){
                                for(int i=0;i<chat_list.size();i++){
                                    if(!user.getUserid().equals(chat_list.get(i).getUserid())){
                                        chat_list.add(user);
                                    }
                                }

                            } else {
                                chat_list.add(user);
                            }
                        }
                    }


                   }

                user_disply_adapter = new user_disply_adapter(getContext(),chat_list, true);
                recyclerView.setAdapter(user_disply_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}