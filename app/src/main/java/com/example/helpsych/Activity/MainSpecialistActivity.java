package com.example.helpsych.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.helpsych.Adapters.SliderPagerAdapter;
import com.example.helpsych.Adapters.SliderPagerSpecialistAdapter;
import com.example.helpsych.Fragments.ApproachAdminFragment;
import com.example.helpsych.Fragments.ArticleAdminFragment;
import com.example.helpsych.Fragments.ArticleFragment;
import com.example.helpsych.Fragments.ArticleSpecialistFragment;
import com.example.helpsych.Fragments.ChatFragment;
import com.example.helpsych.Fragments.ChatSpecialistFragment;
import com.example.helpsych.Fragments.HelpFragment;
import com.example.helpsych.Fragments.ProfileFragment;
import com.example.helpsych.Fragments.ProfileSpecialistFragment;
import com.example.helpsych.Fragments.ReportAdminFragment;
import com.example.helpsych.Fragments.ReportFragment;
import com.example.helpsych.Fragments.ReportSpecialistFragment;
import com.example.helpsych.Fragments.UsersAdminFragment;
import com.example.helpsych.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainSpecialistActivity extends AppCompatActivity {

    private ViewPager pager;
    private PagerAdapter pagerAdapterSpecialist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_specialist);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation_specialist);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<Fragment> list = new ArrayList<>();
        list.add(new ArticleSpecialistFragment());
        list.add(new ChatSpecialistFragment());
        list.add(new ReportSpecialistFragment());
        list.add(new ProfileSpecialistFragment());

        pager = findViewById(R.id.pagerSpecialist);
        pagerAdapterSpecialist = new SliderPagerSpecialistAdapter(getSupportFragmentManager(),list);
        pager.setAdapter(pagerAdapterSpecialist);

        pager.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                switch (pager.getCurrentItem()) {
                    case 0:
                        navigation.setSelectedItemId(R.id.ArticleSpecialistFragment);
                        break;
                    case 1:
                        navigation.setSelectedItemId(R.id.ChatSpecialistFragment);
                        break;
                    case 2:
                        navigation.setSelectedItemId(R.id.ReportSpecialistFragment);
                        break;
                    case 3:
                        navigation.setSelectedItemId(R.id.ProfileSpecialistFragment);
                        break;
                }
            }
        });
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.ArticleSpecialistFragment:
                    pager.setCurrentItem(0);
                    return true;
                case R.id.ChatSpecialistFragment:
                    pager.setCurrentItem(1);
                    return true;
                case R.id.ReportSpecialistFragment:
                    pager.setCurrentItem(2);
                    return true;
                case R.id.ProfileSpecialistFragment:
                    pager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };
}