package com.example.chat_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class login_user_page extends AppCompatActivity {

    EditText login_email, login_password;

    Button login_btn;
    ProgressDialog progressDialog;



    TextView google_login, facebook_login, back_to_new_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user_page);

        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        login_btn = findViewById(R.id.login_btn);
        google_login = findViewById(R.id.google_login_btn);
        facebook_login = findViewById(R.id.facebook_login_btn);
        back_to_new_user = findViewById(R.id.new_user_back_btn);




        progressDialog = new ProgressDialog(login_user_page.this);
        progressDialog.setIcon(R.drawable.facebook_logo);
        progressDialog.setTitle("HOLD ON");
        progressDialog.setMessage("Getting you IN");


        back_to_new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_user_page.this, new_user_page.class);
                startActivity(intent);
            }
        });


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email  = login_email.getText().toString();
                String password = login_password.getText().toString();
                String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(email);


                if(email.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Email is Empty",Toast.LENGTH_LONG).show();
                    login_email.setError("Email is Empty");
                }
                else if(!matcher.matches()){
                    Toast.makeText(getApplicationContext(),"Enter Valid Email Id",Toast.LENGTH_LONG).show();
                    login_email.setError("Enter Valid Email Id");
                }
                else if(password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Password is Empty",Toast.LENGTH_LONG).show();
                    login_password.setError("Password is Empty");
                }
                else if(password.length() < 8){
                    Toast.makeText(getApplicationContext(),"Password is Not Strong",Toast.LENGTH_LONG).show();
                    login_password.setError("Password should contain atleast 8 characters");
                }
                else{

                    login_user(email,password);

                }
            }

            private void login_user(String email, String password) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.show();
                            Toast.makeText(login_user_page.this,"Welcome Back!",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(login_user_page.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            progressDialog.dismiss();
                            finish();
                        }
                        else{
                            Toast.makeText(login_user_page.this, "Something went wrong! Ensure that the email or password is correct or Verfication of email may not be done", Toast.LENGTH_LONG).show();
                            login_email.setError("Check your username");
                            login_email.requestFocus();
                            login_password.setError("Check your Password");
                            login_password.requestFocus();
                        }
                    }
                });



            }
        });



    }
}