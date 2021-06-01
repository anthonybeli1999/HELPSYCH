package com.example.helpsych.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class SliderPagerSpecialistAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentListSpecialist;

    public SliderPagerSpecialistAdapter(FragmentManager fm, List<Fragment> fragmentList){
        super(fm);
        this.fragmentListSpecialist = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentListSpecialist.get(position);
    }

    @Override
    public int getCount() {
        return fragmentListSpecialist.size();
    }
}
