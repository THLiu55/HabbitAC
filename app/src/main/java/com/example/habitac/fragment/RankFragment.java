package com.example.habitac.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.habitac.activity.Login;
import com.example.habitac.activity.Main;
import com.example.habitac.adapter.LeaderboardAdapter;
import com.example.habitac.adapter.RankAdapter;

import com.example.habitac.database.User;
import com.example.habitac.model.SharedViewModel;
import com.google.android.material.tabs.TabLayout;


import com.example.habitac.R;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class RankFragment extends Fragment {
//
//    private TabLayout tabLayout;
//    private ViewPager viewPager;
//    private FragmentManager fragmentManager;
//    private RankAdapter rankAdapter;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        View root = inflater.inflate(R.layout.fragment_rank, container, false);
//
//        tabLayout = (TabLayout) root.findViewById(R.id.tabs);
//        viewPager = (ViewPager) root.findViewById(R.id.viewpager);
//
//        fragmentManager = getChildFragmentManager();
//        rankAdapter = new RankAdapter(fragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        rankAdapter.addFragment(new LeaderBoardFragment(), "Rank");
//        rankAdapter.addFragment(new EquipmentFragment(), "Equipment");
//
//        viewPager.setAdapter(rankAdapter);
//
//        tabLayout.addTab(tabLayout.newTab().setText("Rank"));
//        tabLayout.addTab(tabLayout.newTab().setText("Equipment"));
//        tabLayout.setupWithViewPager(viewPager);
//
//        return root;
//
//    }
    TextView myHighestRank, myName,myRank,myLevel;
    RecyclerView rankView;
    LeaderboardAdapter leaderboardAdapter;
    SharedViewModel sharedViewModel;
    User loggedUser;
    MutableLiveData<List<User>> allUsers;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_rank_content, container, false);

        sharedViewModel = new ViewModelProvider(Login.login).get(SharedViewModel.class);
        loggedUser = sharedViewModel.getUser();

        myName = root.findViewById(R.id.my_rank_name);
        myRank = root.findViewById(R.id.my_rank_num);
        myLevel = root.findViewById(R.id.my_rank_level_num);
        myHighestRank = root.findViewById(R.id.my_highest_ranking);
        rankView = root.findViewById(R.id.user_ranking_view);

        myName.setText(loggedUser.getUser_name());
        myHighestRank.setText(loggedUser.getHighestRank() + "");
        myLevel.setText(loggedUser.getCurrentLevel() + "");
        myRank.setText("7");
        leaderboardAdapter = new LeaderboardAdapter(new LinkedList<>(), getContext());
        allUsers = new MutableLiveData<>(new LinkedList<>());


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rankView.setLayoutManager(layoutManager);

//TODO:
//        leaderboardAdapter = new LeaderboardAdapter();


        rankView.setAdapter(leaderboardAdapter);
        leaderboardAdapter.setUserList(allUsers.getValue());
        allUsers.observe(requireActivity(), new Observer<List<User>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<User> users) {
                leaderboardAdapter.setUserList(users);
                leaderboardAdapter.notifyDataSetChanged();
            }
        });


        List<User> users = new LinkedList<>();
        User u1 = new User();
        u1.setUser_name("tianhao");
        u1.setCurrentLevel(0);
        User u2 = new User();
        u2.setUser_name("Riley");
        u2.setCurrentLevel(1);
        User u3 = new User();
        u3.setUser_name("tony");
        u3.setCurrentLevel(3);
        User u4 = new User();
        u4.setUser_name("tianhaoLiu");
        u4.setCurrentLevel(5);
        User u5 = new User();
        u5.setUser_name("Hts");
        u5.setCurrentLevel(2);
        User u6 = new User();
        u6.setUser_name("Steven");
        u6.setCurrentLevel(10);
        User u7 = new User();
        u7.setUser_name("tyd");
        u7.setCurrentLevel(11);
        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
        users.add(u5);
        users.add(u6);
        users.add(u7);
        Collections.sort(users, (a, b) -> {return b.getCurrentLevel() - a.getCurrentLevel();});
        allUsers.setValue(users);



        return root;
    }
}



