package com.example.habitac.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.habitac.R;
import com.example.habitac.database.User;
import com.example.habitac.model.SharedViewModel;

public class Profile extends AppCompatActivity {

    SharedViewModel sharedViewModel;
    User loggedUser;
    TextView textView_profile_coin, textView_profile_level;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.profile_toolBar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        sharedViewModel = new ViewModelProvider(Login.login).get(SharedViewModel.class);
        loggedUser = sharedViewModel.getUser();

        textView_profile_coin = findViewById(R.id.profile_user_coin);
        textView_profile_level = findViewById(R.id.profile_user_level);

        textView_profile_coin.setText(" " + loggedUser.getCurrentCoin());
        textView_profile_level.setText(" " + loggedUser.getCurrentLevel());


    }
}