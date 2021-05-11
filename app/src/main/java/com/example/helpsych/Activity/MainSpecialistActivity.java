package com.example.helpsych.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.helpsych.Fragments.ArticleFragment;
import com.example.helpsych.Fragments.ArticleSpecialistFragment;
import com.example.helpsych.Fragments.ChatFragment;
import com.example.helpsych.Fragments.ChatSpecialistFragment;
import com.example.helpsych.Fragments.HelpFragment;
import com.example.helpsych.Fragments.ProfileFragment;
import com.example.helpsych.Fragments.ProfileSpecialistFragment;
import com.example.helpsych.Fragments.ReportFragment;
import com.example.helpsych.Fragments.ReportSpecialistFragment;
import com.example.helpsych.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainSpecialistActivity extends AppCompatActivity {

    ArticleSpecialistFragment articleSpecialistFragment = new ArticleSpecialistFragment();
    ChatSpecialistFragment chatSpecialistFragment = new ChatSpecialistFragment();
    ReportSpecialistFragment reportSpecialistFragment = new ReportSpecialistFragment();
    ProfileSpecialistFragment profileSpecialistFragment = new ProfileSpecialistFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_specialist);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation_specialist);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container_specialist, articleSpecialistFragment);
        transaction.commit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.ArticleSpecialistFragment:
                    loadFragment(articleSpecialistFragment);
                    return true;
                case R.id.ChatSpecialistFragment:
                    loadFragment(chatSpecialistFragment);
                    return true;
                case R.id.ReportSpecialistFragment:
                    loadFragment(reportSpecialistFragment);
                    return true;
                case R.id.ProfileSpecialistFragment:
                    loadFragment(profileSpecialistFragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container_specialist, fragment);
        transaction.commit();
    }
}