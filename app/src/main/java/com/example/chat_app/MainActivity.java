package com.example.chat_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements ChipNavigationBar.OnItemSelectedListener {

    ChipNavigationBar chipNavigationBar;


    DatabaseReference databaseReference;
    Toolbar toolbar;

    FirebaseUser fuser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(new Home_Fragment());

        chipNavigationBar = findViewById(R.id.navigation_bar);

        toolbar= findViewById(R.id.toolbar_top_main);



        toolbar.inflateMenu(R.menu.option_menu);

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        chipNavigationBar.setOnItemSelectedListener(this);









    }

    private void loadFragment(Fragment fragment) {

        if(fragment!= null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,fragment).commit();

        }
        else{
            Toast.makeText(getApplicationContext(), "Error",Toast.LENGTH_SHORT).show();
        }


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bottom_menu,menu);
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);


    }

    @Override
    public void onItemSelected(int i) {
        Fragment fragment = null;


        switch (i){
            case R.id.home_nav:
                fragment = new Home_Fragment();
                break;
            case R.id.group_nav:
                fragment = new groups_Fragment();
                break;
            case R.id.settings_navi:
                fragment = new settings_Fragment();
                break;

        }

        loadFragment(fragment);

    }


    private void status(String status){
        databaseReference = FirebaseDatabase.getInstance().getReference("Registered users").child(fuser.getUid());

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