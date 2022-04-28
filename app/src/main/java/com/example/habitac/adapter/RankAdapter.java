package com.example.habitac.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class RankAdapter extends FragmentPagerAdapter {

    List<Fragment> rankFragment;
    List<String> rankFragmentsTitle ;

    public RankAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        rankFragment = new ArrayList<>();
        rankFragmentsTitle = new ArrayList<>();
    }

    public void addFragment(Fragment fragment, String fragmentTitle) {
        rankFragment.add(fragment);
        rankFragmentsTitle.add(fragmentTitle);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return rankFragment.get(position);
    }

    @Override
    public int getCount() {
        return rankFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return rankFragmentsTitle.get(position);
    }
}
