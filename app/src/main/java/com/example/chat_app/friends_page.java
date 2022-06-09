package com.example.chat_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class friends_page extends AppCompatActivity {


    Toolbar toolbar;

    RecyclerView recyclerView;

    user_disply_adapter user_disply_adapter;
    private List<User> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_page);
        toolbar= findViewById(R.id.toolbar_top_main);
        setActionBar(toolbar);


        recyclerView = findViewById(R.id.friends_display);




        toolbar.setTitle("Chat with Friends");


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        userList = new ArrayList<>();

        readUser();





    }

    private void readUser() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Registered users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              userList.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    User user = snapshot1.getValue(User.class);

                    if(!user.getUserid().equals(firebaseUser.getUid())){
                       userList.add(user);
                    }

                }

                user_disply_adapter = new user_disply_adapter(friends_page.this,userList,false);
                recyclerView.setAdapter(user_disply_adapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}