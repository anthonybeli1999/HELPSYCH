package com.example.helpsych.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.helpsych.Fragments.ArticleFragment;
import com.example.helpsych.Fragments.ChatFragment;
import com.example.helpsych.Fragments.HelpFragment;
import com.example.helpsych.Fragments.PerfilFragment;
import com.example.helpsych.Fragments.ReportFragment;
import com.example.helpsych.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ArticleFragment articleFragment = new ArticleFragment();
    ChatFragment chatFragment = new ChatFragment();
    HelpFragment helpFragment = new HelpFragment();
    ReportFragment reportFragment = new ReportFragment();
    PerfilFragment perfilFragment = new PerfilFragment();

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
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.ArticleFragment:
                    loadFragment(articleFragment);
                    return true;
                case R.id.ChatFragment:
                    loadFragment(chatFragment);
                    return true;
                case R.id.HelpFragment:
                    loadFragment(helpFragment);
                    return true;
                case R.id.ReportFragment:
                    loadFragment(reportFragment);
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
}