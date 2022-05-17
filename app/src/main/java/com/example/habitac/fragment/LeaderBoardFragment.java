package com.example.habitac.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.habitac.R;
import com.example.habitac.activity.Login;
import com.example.habitac.adapter.LeaderboardAdapter;
import com.example.habitac.database.User;
import com.example.habitac.model.SharedViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class LeaderBoardFragment extends Fragment {

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
        myRank.setText(loggedUser.getCurrentRank() + "");
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

        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                Collections.sort(list, (a, b) -> {return a.getCurrentRank() - b.getCurrentRank();});
                allUsers.setValue(list);
            }
        });







        return root;
    }
}