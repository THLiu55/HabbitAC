package com.example.habitac.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.habitac.utils.LanguageUtil;

import java.util.ArrayList;
import java.util.List;

public class BasicActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstantState) {
        super.onCreate(savedInstantState);
        Log.d("BaseActivity",getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
    @Override
    public void onRestoreInstanceState(Bundle bundle){
        super.onRestoreInstanceState(bundle);
        Log.e("TAG","onRestoreInstanceState");
    }

}
