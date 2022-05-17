package com.example.habitac.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
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


public class LeaderBoardFragment extends Fragment {

    TextView myHighestRank, myName,myRank,myLevel;
    RecyclerView rankView;
    LeaderboardAdapter leaderboardAdapter;
    SharedViewModel sharedViewModel;
    User loggedUser;

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




        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rankView.setLayoutManager(layoutManager);

//TODO:
//        leaderboardAdapter = new LeaderboardAdapter();


        rankView.setAdapter(leaderboardAdapter);









        return root;
    }
}