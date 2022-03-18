package com.example.habitac.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.habitac.R;

public class ForgetPwd extends AppCompatActivity {


    boolean email_valid;
    // 文本输入
    EditText email;

    Button button_email_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        // 隐藏 actionbar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        init();

        button_email_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ForgetPwd.this, email.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init(){
        email = findViewById(R.id.email_forgetpwd);
        button_email_send = findViewById(R.id.send_code_forget);
    }

    public static void actionStart(Context context, String data, String data2) {
        Intent intent = new Intent(context, ForgetPwd.class);
        intent.putExtra("param1", data);
        intent.putExtra("param2", data);
        context.startActivity(intent);
    }
}