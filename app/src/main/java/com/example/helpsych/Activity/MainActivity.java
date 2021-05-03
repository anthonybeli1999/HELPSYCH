package com.example.helpsych.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.helpsych.Fragments.ArticleFragment;
import com.example.helpsych.Fragments.ChatFragment;
import com.example.helpsych.Fragments.HelpFragment;
import com.example.helpsych.Fragments.PerfilFragment;
import com.example.helpsych.Fragments.ReportFragment;
import com.example.helpsych.Fragments.FindUsersFragment;

import com.example.helpsych.Fragments.RequestFragment;
import com.example.helpsych.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    ArticleFragment articleFragment = new ArticleFragment();
    ChatFragment chatFragment = new ChatFragment();
    HelpFragment helpFragment = new HelpFragment();
    ReportFragment reportFragment = new ReportFragment();
    PerfilFragment perfilFragment = new PerfilFragment();
    FindUsersFragment findUsersFragment = new FindUsersFragment();
    RequestFragment requestFragment = new RequestFragment();

    //Thats what im doing
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private String currentUserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, articleFragment);
        transaction.commit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Thats what Im doing
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        //currentUserID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();

    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.ArticleFragment:
                    loadFragment(requestFragment);
                    return true;
                case R.id.ChatFragment:
                    loadFragment(chatFragment);
                    return true;
                case R.id.HelpFragment:
                    loadFragment(helpFragment);
                    return true;
                case R.id.ReportFragment:
                    loadFragment(findUsersFragment);
                    return true;
                case R.id.PerfilFragment:
                    loadFragment(perfilFragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }


    @Override
    protected void onStart()
    {
        super.onStart();

        if (currentUser == null)
        {
            SendUserToLoginActivity();
        }
        else
        {
            //updateUserStatus("online");

            VerifyUserExistance();
        }
    }

    private void VerifyUserExistance()
    {
        String currentUserID = mAuth.getCurrentUser().getUid();

        RootRef.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if ((dataSnapshot.child("name").exists()))
                {
                    Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SendUserToSettingsActivity();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void SendUserToLoginActivity()
    {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        //finish();
    }


    private void SendUserToSettingsActivity()
    {
        Intent settingsIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(settingsIntent);
    }



}