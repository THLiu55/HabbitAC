package com.example.habitac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    // 输入文本
    EditText editText_accountId, editText_password;
    // 按钮
    Button button_register, button_login, button_password_findBack;
    // 记住密码
    CheckBox rememberPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        // 设置 'register' 按钮相应事件
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp.actionStart(MainActivity.this, null, null);
            }
        });
    }

    // 初始化所有 UI 组件（与前端页面相连）
    private void init() {
        editText_password = findViewById(R.id.password);
        editText_accountId = findViewById(R.id.account);
        button_login = findViewById(R.id.login_bnt);
        button_register = findViewById(R.id.signUp_bnt);
        button_password_findBack = findViewById(R.id.forgetPassword_bnt);
    }

    // 进入本页面请调用：
    // 调用格式：目标页.actionStart(起点，携带数据1， 携带数据2);
    public static void actionStart(Context context, String data, String data2) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("param1", data);
        intent.putExtra("param2", data);
        context.startActivity(intent);
    }
}