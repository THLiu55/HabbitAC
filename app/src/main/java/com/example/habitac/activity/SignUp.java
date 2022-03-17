package com.example.habitac.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.habitac.R;
import com.example.habitac.database.User;
import com.example.habitac.utils.MailSender;

import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class SignUp extends AppCompatActivity {
    // 邮箱 valid 检验 （正则表达式pattern）
    public static final String REGEX_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

     // 前端组件的实例化
    private EditText editText_account, editText_pass, editText_pass2, editText_email, editText_code;
    private Button button_send_code, button_confirm, button_returnLogin;
    // 后端对输入数据的存储
    private String user_name, password, password2, email, verify_code;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // 隐藏 actionbar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        init();
        // 设置 'return to login page' 按钮相应事件
        button_returnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login.actionStart(SignUp.this, null, null);
            }
        });

        // 设置 'send' 按钮相应事件
        button_send_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 检测是否已经发送邮件
                boolean sent = false;
                // 发送邮件
                if (basicCheck() && databaseCheck()) {
                    sent = true;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                button_send_code.setClickable(false);
                                code = codeInit();
                                MailSender sender = new MailSender("habitac@hutian.su", "lthSB666");
                                sender.sendMail("welcome to HabitAC","your verification code is " + code,"habitAC@hutian.su", email);
                            } catch (GeneralSecurityException | MessagingException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                // todo: 倒计时开发（需要改布局）
            }
        });



        // 设置 'confirm' 按钮相应事件
        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (basicCheck() && databaseCheck()) {
                    verify_code = editText_code.getText().toString();
                    if (verify_code.equals(code)) {
                        User newUser = new User(user_name, password, email);
                        newUser.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                Toast.makeText(SignUp.this, "Successful!", Toast.LENGTH_SHORT).show();
                                Login.actionStart(SignUp.this, user_name, password2);
                            }
                        });
                    } else {
                        editText_code.setError("wrong code");
                    }
                }
            }
        });
    }

    private String codeInit() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(9));
        }
        return sb.toString();
    }

    // 初始化所有 UI 组件（与前端页面相连）
    private void init() {
        editText_account = findViewById(R.id.user_name_signup);
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

    // 基本的输入检查 （是否符合hint指示，不查数据库）
    private boolean basicCheck() {
        boolean allCorrect = true;   // 检测是否有输入错误 （有->false; 无->true）

        // 收集当前输入数据：
        user_name = editText_account.getText().toString();
        password = editText_pass.getText().toString();
        password2 = editText_pass2.getText().toString();
        email = editText_email.getText().toString();

        // 检测 user name 是否符合规范
        if (user_name.trim().isEmpty()) {
            editText_account.setError("can't be blank");
            allCorrect = false;
        }
        if (user_name.length() < 4) {
            editText_account.setError("user name too short");
            allCorrect = false;
        }
        if (user_name.length() > 10) {
            editText_account.setError("user name too long");
            allCorrect = false;
        }
        if (user_name.contains(" ")) {
            editText_account.setError("can't contain space");
            allCorrect = false;
        }

        // 检测密码输入是否符合要求
        if (password.trim().isEmpty()) {
            editText_pass.setError("can't be blank");
            allCorrect = false;
        }
        if (password.length() < 8) {
            editText_pass.setError("password too short");
            allCorrect = false;
        }
        if (password.length() > 16) {
            editText_pass.setError("password too long");
            allCorrect = false;
        }

        // 检测两次输入密码是否相同
        if (!password.equals(password2)) {
            editText_pass2.setError("password not match");
            allCorrect = false;
        }

        // 检测 email 格式是否正确
        Pattern p = Pattern.compile(REGEX_EMAIL);
        Matcher matcher = p.matcher(email);
        if (!matcher.matches()) {
            editText_email.setError("wrong email format");
            allCorrect = false;
        }
        return allCorrect;
    }

    // 检查新用户信息是否与数据库中已有用户信息冲突
    private boolean databaseCheck() {
        final boolean[] allCorrect = {true};   // 检测是否有输入错误 （有->false; 无->true）

        // 收集当前输入数据：
        user_name = editText_account.getText().toString();
        email = editText_email.getText().toString();

        // 检查用户名是否重复
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("user_name", user_name);
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e != null) {
                    allCorrect[0] = false;
                    editText_account.setError("user name exist");
                }
            }
        });
        // 检测邮箱是否已存在
        bmobQuery.addWhereEqualTo("email", email);
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e != null) {
                    allCorrect[0] = false;
                    editText_email.setError("email is used");
                }
            }
        });
        return allCorrect[0];
    }

}