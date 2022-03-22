package com.example.habitac.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.habitac.R;
import com.example.habitac.database.User;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class Login extends BasicActivity {
    // 输入文本
    EditText editText_accountId, editText_password;
    // 按钮
    Button button_register, button_login, button_password_findBack;
    // 记住密码
    CheckBox rememberPass;
    // 密码 & 用户名
    String userName, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, "ebbe137833bf421a6e3584f81e56b462");
        // 隐藏 actionbar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        init();
        // 设置 'register' 按钮相应事件
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp.actionStart(Login.this, null, null);
            }
        });
        // 设置 'log in' 按钮响应事件
        button_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                password = editText_password.getText().toString();
                userName = editText_accountId.getText().toString();
                BmobQuery<User> bmobQuery = new BmobQuery<>();
                bmobQuery.addWhereEqualTo("user_name", userName);
                bmobQuery.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if (e == null) {
                            if (list.size() == 0) {
                                editText_accountId.setError("user name ont exist");
                            } else {
                                User user = list.get(0);
                                String userPass = user.getPassword();
                                if (!userPass.equals(password)) {
                                    editText_password.setError("wrong password");
                                } else {
                                    Toast.makeText(Login.this, "welcome", Toast.LENGTH_SHORT).show();
                                    Home.actionStart(Login.this, null, null);
                                }
                            }
                        } else {
                            Toast.makeText(Login.this, "network error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        button_password_findBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgetPwd.actionStart(Login.this, null, null);
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
        Intent intent = new Intent(context, Login.class);
        intent.putExtra("param1", data);
        intent.putExtra("param2", data);
        context.startActivity(intent);
    }
}