package com.example.habitac.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.habitac.R;
import com.example.habitac.adapter.TaskRepositoryAdapter;
import com.example.habitac.database.User;
import com.example.habitac.fragment.TaskRepositoryFragment;
import com.example.habitac.model.SharedViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Main extends BasicActivity implements NavigationView.OnNavigationItemSelectedListener {
    // bottom navigation
    BottomNavigationView bottomNavigationView;
    AppBarConfiguration appBarConfiguration;
    NavController bottomNavController;

    // drawer navigation
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    NavController drawerNavController;
    NavHostFragment drawerNavHostFragment;
    SharedViewModel sharedViewModel;
    String user_name;
    TextView textView_user_name, textView_email;
    User loggedUser;


//    TextView userName;


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


        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navView);

        sharedViewModel = new ViewModelProvider(Login.login).get(SharedViewModel.class);
        loggedUser = sharedViewModel.getUser();

        //rotation icon
        actionBarDrawerToggle =  new ActionBarDrawerToggle(this, drawerLayout , toolbar , R.string.nav_drawer_open,R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.show_all_tasks) {
                    TaskRepositoryFragment fragment = new TaskRepositoryFragment();
                    fragment.show(getSupportFragmentManager(), "nb");
                }
                return true;
            }
        });


        NavigationView navigationView = (NavigationView) findViewById(R.id.navView);
        View navHeader = navigationView.getHeaderView(0);
        textView_user_name = navHeader.findViewById(R.id.nav_header_username);
        textView_user_name.setText(loggedUser.getUser_name());
        textView_email = navHeader.findViewById(R.id.nav_header_email);
        textView_email.setText(loggedUser.getEmail());

    }

    // 进入本页面请调用：
    // 调用格式：目标页.actionStart(起点，携带数据1， 携带数据2);
    public static void actionStart(Context context, String userName, String data2) {
        Intent intent = new Intent(context, Main.class);
        intent.putExtra("param1", userName);
        intent.putExtra("param2", data2);
        context.startActivity(intent);
    }


    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_help:
                Intent intent1 = new Intent(Main.this,Help.class);
                startActivity(intent1);
                break;

            case R.id.nav_setting:
                Intent intent2 = new Intent(Main.this,Setting.class);
                startActivity(intent2);
                break;

            case R.id.nav_profile:
                Intent intent3 = new Intent(Main.this,Profile.class);
                startActivity(intent3);
                break;

            case R.id.nav_contact:
                Intent intent4 = new Intent(Main.this,ContactUs.class);
                startActivity(intent4);
                break;

            case R.id.nav_logOut:
                Toast.makeText(this, "Bye", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    //Drawer: press back
    @Override
    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task_repository, menu);
        return true;
    }
}