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
import com.example.helpsych.Adapters.SliderPagerAdminAdapter;
import com.example.helpsych.Fragments.ApproachAdminFragment;
import com.example.helpsych.Fragments.ArticleAdminFragment;
import com.example.helpsych.Fragments.ArticleFragment;
import com.example.helpsych.Fragments.ChatFragment;
import com.example.helpsych.Fragments.HelpFragment;
import com.example.helpsych.Fragments.ProfileFragment;
import com.example.helpsych.Fragments.ReportAdminFragment;
import com.example.helpsych.Fragments.ReportFragment;
import com.example.helpsych.Fragments.TestsAdminFragment;
import com.example.helpsych.Fragments.UsersAdminFragment;
import com.example.helpsych.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainAdminActivity extends AppCompatActivity {

    private ViewPager pager;
    private PagerAdapter pagerAdapterAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation_admin);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<Fragment> list = new ArrayList<>();
        list.add(new ArticleAdminFragment());
        list.add(new ApproachAdminFragment());
        list.add(new UsersAdminFragment());
        list.add(new TestsAdminFragment());
        list.add(new ReportAdminFragment());

        pager = findViewById(R.id.pagerAdmin);
        pagerAdapterAdmin = new SliderPagerAdminAdapter(getSupportFragmentManager(),list);
        pager.setAdapter(pagerAdapterAdmin);

        pager.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                switch (pager.getCurrentItem()) {
                    case 0:
                        navigation.setSelectedItemId(R.id.ArticleAdminFragment);
                        break;
                    case 1:
                        navigation.setSelectedItemId(R.id.ApproachAdminFragment);
                        break;
                    case 2:
                        navigation.setSelectedItemId(R.id.UsersAdminFragment);
                        break;
                    case 3:
                        navigation.setSelectedItemId(R.id.TestAdminFragment);
                        break;
                    case 4:
                        navigation.setSelectedItemId(R.id.ReportAdminFragment);
                        break;
                }
            }
        });

    }


    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.ArticleAdminFragment:
                    pager.setCurrentItem(0);
                    return true;
                case R.id.ApproachAdminFragment:
                    pager.setCurrentItem(1);
                    return true;
                case R.id.UsersAdminFragment:
                    pager.setCurrentItem(2);
                    return true;
                case R.id.TestAdminFragment:
                    pager.setCurrentItem(3);
                    return true;
                case R.id.ReportAdminFragment:
                    pager.setCurrentItem(4);
                    return true;
            }
            return false;
        }
    };
}