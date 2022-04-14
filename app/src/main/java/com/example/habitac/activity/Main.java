package com.example.habitac.activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import com.example.habitac.R;
import com.example.habitac.database.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Main extends BasicActivity {
    // bottom navigation
    BottomNavigationView bottomNavigationView;
    AppBarConfiguration appBarConfiguration;
    NavController bottomNavController;
    // drawer navigation
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    NavController drawerNavController;
    NavHostFragment drawerNavHostFragment;


    protected static User loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_calendar, R.id.navigation_rank, R.id.navigation_me).build();
        bottomNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, bottomNavController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, bottomNavController);
//        Intent()
    }

    // 进入本页面请调用：
    // 调用格式：目标页.actionStart(起点，携带数据1， 携带数据2);
    public static void actionStart(Context context, String userName, String data2) {
        Intent intent = new Intent(context, Main.class);
        intent.putExtra("param1", userName);
        intent.putExtra("param2", data2);
        loggedUser = User.findUser(userName);
        context.startActivity(intent);
    }
}