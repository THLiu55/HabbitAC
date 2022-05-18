package com.example.habitac.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import androidx.viewpager.widget.ViewPager;

import com.example.habitac.adapter.RankAdapter;

import com.google.android.material.tabs.TabLayout;


import com.example.habitac.R;
public class RankFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentManager fragmentManager;
    private RankAdapter rankAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_rank, container, false);

        tabLayout = (TabLayout) root.findViewById(R.id.tabs);
        viewPager = (ViewPager) root.findViewById(R.id.viewpager);

        fragmentManager = getChildFragmentManager();
        rankAdapter = new RankAdapter(fragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        rankAdapter.addFragment(new LeaderBoardFragment(), "Rank");
        rankAdapter.addFragment(new EquipmentFragment(), "Equipment");

        viewPager.setAdapter(rankAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("Rank"));
        tabLayout.addTab(tabLayout.newTab().setText("Equipment"));
        tabLayout.setupWithViewPager(viewPager);

        return root;
    }


}



