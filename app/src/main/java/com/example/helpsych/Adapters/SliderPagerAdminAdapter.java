package com.example.helpsych.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class SliderPagerAdminAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentListAdmin;

    public SliderPagerAdminAdapter(FragmentManager fm, List<Fragment> fragmentList){
        super(fm);
        this.fragmentListAdmin = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentListAdmin.get(position);
    }

    @Override
    public int getCount() {
        return fragmentListAdmin.size();
    }
}
