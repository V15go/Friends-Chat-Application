package com.example.chat_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.yalantis.ucrop.view.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;


public class settings_Fragment extends Fragment {
    private View PrivateChatsView;

     CircleImageView circleImageView;

     EditText name_settings, about_me_settings;

     Button delete_btn, sigh_out_btn;
   FloatingActionButton update_profile_image;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private FirebaseUser user;

    StorageReference storageReference;
    private static final int IMAGE_Req = 1;
    private Uri imageuri;
    private StorageTask storageTask;
    ActivityResultLauncher activityResultLauncher;









    public settings_Fragment(){

    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        PrivateChatsView = inflater.inflate(R.layout.fragment_settings_, container, false);

        circleImageView = PrivateChatsView.findViewById(R.id.profile_pic_user);
        update_profile_image = PrivateChatsView.findViewById(R.id.change_profile_pic);
        name_settings = PrivateChatsView.findViewById(R.id.username_name);
        about_me_settings = PrivateChatsView.findViewById(R.id.about_one_line);
        delete_btn = PrivateChatsView.findViewById(R.id.delete_account);
        sigh_out_btn = PrivateChatsView.findViewById(R.id.sign_out_btn);



        sigh_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(),new_user_page.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),friends_page.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Registered users");

        storageReference = FirebaseStorage.getInstance().getReference("uploads");


        reference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User  user1= snapshot.getValue(User.class);

                name_settings.setText(user1.getUsername());
                about_me_settings.setText(user1.getAbout_one_line());
                if(user1.getImageUrl().equals("default")){
                    circleImageView.setImageResource(R.mipmap.ic_launcher_round);
                }
                else{
                    Glide.with(getContext()).load(user1.getImageUrl()).into(circleImageView);
                }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        
        update_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().setAspectRatio(1,1);
                update_dp();


            }


        });






        return PrivateChatsView;
    }

    private void update_dp() {
    }






}