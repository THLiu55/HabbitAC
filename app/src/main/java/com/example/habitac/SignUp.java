package com.example.habitac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }



    // 进入本页面请调用：
    // 调用格式：目标页.actionStart(起点，携带数据1， 携带数据2);
    public static void actionStart(Context context, String data, String data2) {
        Intent intent = new Intent(context, SignUp.class);
        intent.putExtra("param1", data);
        intent.putExtra("param2", data);
        context.startActivity(intent);
    }


}