package com.example.habitac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {
    EditText editText_account, editText_pass, editText_pass2, editText_email, editText_code;
    Button button_send_code, button_confirm, button_returnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        // 设置 'return to login page' 按钮相应事件
        button_returnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.actionStart(SignUp.this, null, null);
            }
        });
    }

    // 初始化所有 UI 组件（与前端页面相连）
    private void init() {
        editText_account = findViewById(R.id.account_signup);
        editText_pass = findViewById(R.id.password_signup);
        editText_pass2 = findViewById(R.id.password_Re_enter_signup);
        editText_email = findViewById(R.id.email_signup);
        editText_code = findViewById(R.id.code_signup);
        button_confirm = findViewById(R.id.confirm_button_signup);
        button_returnLogin = findViewById(R.id.returnLogin_signup);
        button_send_code = findViewById(R.id.send_code_bnt_signup);
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