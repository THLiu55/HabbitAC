package com.example.habitac.activity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.habitac.R;
import com.example.habitac.utils.LanguageUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Setting extends AppCompatActivity {
    RelativeLayout changeLanguage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        changeLanguage = (RelativeLayout) findViewById(R.id.setting_change_language);
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
    }
}