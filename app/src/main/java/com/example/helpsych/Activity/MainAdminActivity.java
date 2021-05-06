package com.example.helpsych.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.helpsych.Fragments.ApproachAdminFragment;
import com.example.helpsych.Fragments.ArticleAdminFragment;
import com.example.helpsych.Fragments.ArticleFragment;
import com.example.helpsych.Fragments.ChatFragment;
import com.example.helpsych.Fragments.HelpFragment;
import com.example.helpsych.Fragments.PerfilFragment;
import com.example.helpsych.Fragments.ReportAdminFragment;
import com.example.helpsych.Fragments.ReportFragment;
import com.example.helpsych.Fragments.UsersAdminFragment;
import com.example.helpsych.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainAdminActivity extends AppCompatActivity {

    ArticleAdminFragment articleAdminFragment = new ArticleAdminFragment();
    ApproachAdminFragment chatAdminFragment = new ApproachAdminFragment();
    UsersAdminFragment helpAdminFragment = new UsersAdminFragment();
    ReportAdminFragment reportAdminFragment = new ReportAdminFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation_admin);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container_admin, articleAdminFragment);
        transaction.commit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.ArticleAdminFragment:
                    loadFragment(articleAdminFragment);
                    return true;
                case R.id.ApproachAdminFragment:
                    loadFragment(chatAdminFragment);
                    return true;
                case R.id.UsersAdminFragment:
                    loadFragment(helpAdminFragment);
                    return true;
                case R.id.ReportAdminFragment:
                    loadFragment(reportAdminFragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container_admin, fragment);
        transaction.commit();
    }
}