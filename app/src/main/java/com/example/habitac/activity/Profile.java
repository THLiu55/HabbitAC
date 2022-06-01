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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Profile extends AppCompatActivity {

    TextView pName,pLevel,pRanking,pCoin,pHighestRanking;
    ImageView pAvatar;
    SharedViewModel sharedViewModel;
    User loggedUser;
    private String avatarSeed;
    TextView jointime;

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
        pRanking.setText(String.valueOf(loggedUser.getCurrentRank()) + "\n(Visit Rank page to refresh)");

        pHighestRanking = findViewById(R.id.profile_highest_ranking);
        pHighestRanking.setText(String.valueOf(loggedUser.getHighestRank()));

        jointime = findViewById(R.id.jointime);

        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd");
        String created = loggedUser.getCreatedAt();
        String now =  simpleDateFormat.format(new Date(System.currentTimeMillis()));
        try {
            long m = simpleDateFormat.parse(now).getTime() - simpleDateFormat.parse(created.substring(0,10)).getTime();
            long tianshu = m/(1000*60*60*24);
            jointime.setText("You've joined HabitAC for " + tianshu + " days! 🎉");
        } catch (ParseException e) {
            e.printStackTrace();
        }


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