package com.example.helpsych.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.helpsych.Adapters.SliderPagerAdapter;
import com.example.helpsych.Fragments.ArticleFragment;
import com.example.helpsych.Fragments.ChatFragment;
import com.example.helpsych.Fragments.HelpFragment;
import com.example.helpsych.Fragments.ProfileFragment;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private String currentUserID;

    private ViewPager pager;
    private PagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Thats what Im doing
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        currentUserID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();

        List<Fragment> list = new ArrayList<>();
        list.add(new ArticleFragment());
        list.add(new ChatFragment());
        list.add(new HelpFragment());
        list.add(new ReportFragment());
        list.add(new ProfileFragment());

        pager = findViewById(R.id.pager);
        pagerAdapter = new SliderPagerAdapter(getSupportFragmentManager(),list);
        pager.setAdapter(pagerAdapter);


        pager.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                switch (pager.getCurrentItem()) {
                    case 0:
                        navigation.setSelectedItemId(R.id.ArticleFragment);
                        break;
                    case 1:
                        navigation.setSelectedItemId(R.id.ChatFragment);
                        break;
                    case 2:
                        navigation.setSelectedItemId(R.id.HelpFragment);
                        break;
                    case 3:
                        navigation.setSelectedItemId(R.id.ReportFragment);
                        break;
                    case 4:
                        navigation.setSelectedItemId(R.id.PerfilFragment);
                        break;

                }
            }
        });
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.ArticleFragment:
                    pager.setCurrentItem(0);
                    return true;
                case R.id.ChatFragment:
                    pager.setCurrentItem(1);
                    return true;
                case R.id.HelpFragment:
                    pager.setCurrentItem(2);
                    return true;
                case R.id.ReportFragment:
                    pager.setCurrentItem(3);
                    return true;
                case R.id.PerfilFragment:
                    pager.setCurrentItem(4);
                    return true;
            }
            return false;
        }
    };


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
            updateUserStatus("online");

            VerifyUserExistance();
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        if (currentUserID != null)
        {
            updateUserStatus("offline");
        }
    }



    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if (currentUserID != null)
        {
            updateUserStatus("offline");
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
                    //Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
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

    private void updateUserStatus(String state)
    {
        String saveCurrentTime, saveCurrentDate;

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        HashMap<String, Object> onlineStateMap = new HashMap<>();
        onlineStateMap.put("time", saveCurrentTime);
        onlineStateMap.put("date", saveCurrentDate);
        onlineStateMap.put("state", state);

        RootRef.child("Users").child(currentUserID).child("userState")
                .updateChildren(onlineStateMap);

    }


}