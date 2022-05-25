package com.example.habitac.activity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.habitac.R;
import com.example.habitac.database.User;
import com.example.habitac.model.SharedViewModel;
import com.example.habitac.utils.LanguageUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Setting extends AppCompatActivity {
    RelativeLayout changeLanguage, changePassword, changeName, aboutUs;
    SharedViewModel sharedViewModel;
    User loggedUser;
    String userId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sharedViewModel = new ViewModelProvider(Login.login).get(SharedViewModel.class);
        loggedUser = sharedViewModel.getUser();
        userId = loggedUser.getObjectId();
        changeLanguage = (RelativeLayout) findViewById(R.id.setting_change_language);
        changePassword = (RelativeLayout) findViewById(R.id.setting_reset_pwd);
        changeName = (RelativeLayout) findViewById(R.id.setting_reset_name);
        aboutUs = (RelativeLayout) findViewById(R.id.about_us);
        Toolbar toolbar = findViewById(R.id.setting_toolBar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.setting_change_language:
                        //修改配置
                        LanguageUtil.settingLanguage(Setting.this, LanguageUtil.getInstance());
                        //activity銷毀重建
                        Setting.this.recreate();
                        break;

                    default:
                        break;
                }
            }

        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.setting_reset_pwd:
                        ResetPwd.actionStart(Setting.this, userId, null);
                        break;
                    default:
                        break;
                }
            }
        });

        changeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.setting_reset_name:
                        ResetName.actionStart(Setting.this, userId, null);
                        break;
                    default:
                        break;
                }
            }
        });

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.about_us:
                        AboutUs.actionStart(Setting.this, userId, null);
                        break;
                    default:
                        break;
                }
            }
        });

    }
}