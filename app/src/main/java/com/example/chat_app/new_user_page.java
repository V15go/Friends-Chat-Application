package com.example.chat_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class new_user_page extends AppCompatActivity {

    EditText new_user_name, new_user_email, new_user_password;

    Button  sign_up_btn;
    TextView google_sign_up, facebook_sign_up, back_to_login;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_page);


        new_user_name = findViewById(R.id.new_user_name);
        new_user_email = findViewById(R.id.new_user_email);
        new_user_password = findViewById(R.id.new_user_password);
        sign_up_btn = findViewById(R.id.new_user_btn);
        google_sign_up = findViewById(R.id.google_new_user_btn);
        facebook_sign_up = findViewById(R.id.facebook_new_user_btn);
        back_to_login = findViewById(R.id.login_back_btn);



        progressDialog = new ProgressDialog(new_user_page.this);
        progressDialog.setTitle("HOLD ON");
        progressDialog.setMessage("Your account is getting ready");
        progressDialog.setIcon(R.drawable.facebook_logo);



        back_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new_user_page.this, login_user_page.class);
                startActivity(intent);
            }
        });



        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = new_user_name.getText().toString();

                String email = new_user_email.getText().toString();

                String password  = new_user_password.getText().toString();
                String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(email);



                if(name.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Name is Empty",Toast.LENGTH_LONG).show();
                    new_user_name.setError("Name is Emapty");
                }
                else if(email.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Email is Empty",Toast.LENGTH_LONG).show();
                    new_user_email.setError("Email is Empty");
                }
                else if(!matcher.matches()){
                    Toast.makeText(getApplicationContext(),"Enter Valid Email Id",Toast.LENGTH_LONG).show();
                    new_user_email.setError("Enter Valid Email Id");
                }
                else if(password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Password is Empty",Toast.LENGTH_LONG).show();
                    new_user_password.setError("Password is Empty");
                }
                else if(password.length() < 8){
                    Toast.makeText(getApplicationContext(),"Password is Not Strong",Toast.LENGTH_LONG).show();
                    new_user_email.setError("Password should contain atleast 8 characters");
                }
                else{

                    reg_user(name, email, password);
                }
            }

            private void reg_user(String name, String email, String password) {
                FirebaseAuth auth = FirebaseAuth.getInstance();

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.show();
                            String about  = "Available On chat";
                            String ImageUrl = "default";
                            String id = task.getResult().getUser().getUid();
                            User user = new User(name,about, ImageUrl, email,password,id);
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Registered users");
                            reference.child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){


                                        Toast.makeText(new_user_page.this, "You Have been Registered. Please verify your email ID", Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(new_user_page.this,MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);

                                        finish();

                                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                    }
                                    else{
                                        Toast.makeText(new_user_page.this,"Registration failed. Do it again",Toast.LENGTH_LONG).show();

                                    }

                                }
                            });


                        }
                        else {
                            try {
                                throw Objects.requireNonNull(task.getException());
                            }
                            catch (FirebaseAuthWeakPasswordException e){
                                new_user_name.setError("Your Password is too weak, Kindly use a mix of alphabets and numericals");
                                new_user_name.requestFocus();

                            }
                            catch (FirebaseAuthInvalidCredentialsException e){
                                new_user_name.setError("Your email is invalid or already in use. kindly re-entry");
                                new_user_name.requestFocus();
                            }
                            catch (FirebaseAuthUserCollisionException e){
                                new_user_name.setError("The email is already registered with us");
                                new_user_name.requestFocus();
                            }
                            catch (Exception e){
                                Log.e("TAG", e.getMessage());
                                Toast.makeText(new_user_page.this,e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                });

            }
        });



    }
}