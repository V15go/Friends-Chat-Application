package com.example.chat_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Message_activity extends AppCompatActivity {

    Toolbar toolbar;

    CircleImageView profile_pic_reciver;
    TextView name_friend;

    EditText message;

    MessageAdapter messageAdapter;

    RecyclerView recyclerView;
    List<Chat> chats;

    TextView send_message_btn;




    FirebaseUser firebaseUser;
    DatabaseReference reference ,reference1, databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_activity);

        toolbar  = findViewById(R.id.toolbar_message);

        setActionBar(toolbar);
        getActionBar().setTitle("");
        getActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Message_activity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });


        profile_pic_reciver = findViewById(R.id.profile_pic_of_reciver);
        name_friend = findViewById(R.id.name_friend);
        message = findViewById(R.id.message_send);

        recyclerView = findViewById(R.id.chat_recyclerview);
        send_message_btn = findViewById(R.id.send_btn);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);



        Intent intent = getIntent();

        String userid = intent.getStringExtra("Friend Id").toString();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Registered users").child(userid);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                name_friend.setText(user.getUsername());
                if(user.getImageUrl().equals("default")){
                    profile_pic_reciver.setImageResource(R.mipmap.ic_launcher_round);
                }

                read(firebaseUser.getUid(), userid, user.getImageUrl());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        send_message_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = message.getText().toString();

                if(msg.isEmpty()){
                    message.setError("Empty");
                }
                else{
                    send_message(firebaseUser.getUid(), userid, msg);
                }

                message.setText("");
            }
        });














    }

    private void send_message(String sender, String receiver, String message) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


        HashMap<String, Object> map = new HashMap<>();

        map.put("sender", sender);
        map.put("receiver", receiver);
        map.put("message", message);

        databaseReference.child("Chats").push().setValue(map);
    }

    private void read(String myid, String userid1, String imageUrl){
        chats  = new ArrayList<>();

        reference1 = FirebaseDatabase.getInstance().getReference("Chats");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Chat chat = dataSnapshot.getValue(Chat.class);




                        if(chat.getReceiver().equals(myid) && chat.getSender().equals(userid1) || chat.getReceiver().equals(userid1)  && chat.getSender().equals(myid) )
                            chats.add(chat);


                    messageAdapter = new MessageAdapter(Message_activity.this,chats, imageUrl);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void status(String status){
        databaseReference = FirebaseDatabase.getInstance().getReference("Registered users").child(firebaseUser.getUid());

        HashMap<String, Object> map = new HashMap<>();
        map.put("status",status);

        databaseReference.updateChildren(map);
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }






}