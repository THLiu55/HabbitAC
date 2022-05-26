package com.example.habitac.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.habitac.R;
import com.example.habitac.database.User;
import com.example.habitac.model.SharedViewModel;
import com.example.habitac.utils.AvatarGetter;

public class Profile extends AppCompatActivity {

    TextView pName,pLevel,pRanking,pCoin,pHighestRanking;
    ImageView pAvatar;
    SharedViewModel sharedViewModel;
    User loggedUser;
    private String avatarSeed;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedViewModel = new ViewModelProvider(Login.login).get(SharedViewModel.class);
        loggedUser = sharedViewModel.getUser();

        pName = findViewById(R.id.profile_user_ranking);
        pName.setText(loggedUser.getUser_name());

        pLevel = findViewById(R.id.profile_user_level);
        pLevel.setText(String.valueOf(loggedUser.getCurrentLevel()));

        pCoin = findViewById(R.id.profile_user_coin);
        pCoin.setText(String.valueOf(loggedUser.getCurrentCoin()));

        pRanking = findViewById(R.id.profile_current_ranking);
        pRanking.setText(String.valueOf(loggedUser.getCurrentRank()));

        pHighestRanking = findViewById(R.id.profile_highest_ranking);
        pHighestRanking.setText(String.valueOf(loggedUser.getHighestRank()));




        Toolbar toolbar = findViewById(R.id.profile_toolBar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


}