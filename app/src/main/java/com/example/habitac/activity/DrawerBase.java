package com.example.habitac.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.habitac.R;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

public class DrawerBase extends AppCompatActivity {
    private DrawerLayout drawerLayout;


    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_base);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

//        DrawerLayout drawerLayout = findViewById(R.id.drawer);
//        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
//


    }



}
