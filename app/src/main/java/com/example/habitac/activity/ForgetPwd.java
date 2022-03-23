package com.example.habitac.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import javax.mail.MessagingException;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ForgetPwd extends BasicActivity{

    // 文本输入
    EditText email, code;
    Button button_email_send, button_forget_next;
    String checkCode;
    String userId;

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
                String email_str = email.getText().toString();
                BmobQuery<User> bmobQuery = new BmobQuery<>();
                bmobQuery.addWhereEqualTo("email", email_str);
                bmobQuery.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        // 检测是否已经发送邮件
                        boolean sent = false;
                        // 发送邮件
                        if (e == null) {
                            if (list.size() == 0) {
                                email.setError("this email not registered");
                            } else {
                                sent = true;
                                userId = list.get(0).getObjectId();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            checkCode = MailSender.codeInit();
                                            MailSender sender = new MailSender("habitac@hutian.su", "lthSB666");
                                            sender.sendMail("HabitAC find back your password","your verification code is " + checkCode,"habitAC@hutian.su", email_str);
                                        } catch (GeneralSecurityException | MessagingException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                            }
                        } else {
                            Toast.makeText(ForgetPwd.this, "network error", Toast.LENGTH_SHORT).show();
                        }
                        if(sent){
                            new CountDownTimer(30000, 1000) {
                                @SuppressLint("SetTextI18n")
                                public void onTick(long millisUntilFinished) {
                                    button_email_send.setText("" + millisUntilFinished / 1000);
                                    button_email_send.setTextSize(15);
                                }
                                @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
                                public void onFinish() {
                                    button_email_send.setTextSize(10);
                                    button_email_send.setText("SEND");
                                    button_email_send.setClickable(true);
                                }
                            }.start();
                        }
                    }
                });
            }
        });

        button_forget_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputCode = code.getText().toString();
                if (inputCode.equals(checkCode)) {
                    ResetPwd.actionStart(ForgetPwd.this, userId, null);
                } else {
                    code.setError("wrong code");
                }
            }
        });
    }

    private void init(){
        email = findViewById(R.id.email_forgetpwd);
        button_email_send = findViewById(R.id.send_code_forget);
        button_forget_next = findViewById(R.id.confirm_button_forgot);
        code = findViewById(R.id.code_forget_pass);
    }

    public static void actionStart(Context context, String data, String data2) {
        Intent intent = new Intent(context, ForgetPwd.class);
        intent.putExtra("param1", data);
        intent.putExtra("param2", data2);
        context.startActivity(intent);
    }
}