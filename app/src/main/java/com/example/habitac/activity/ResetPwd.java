package com.example.habitac.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.habitac.R;
import com.example.habitac.database.User;
import com.example.habitac.model.SharedViewModel;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ResetPwd extends BasicActivity {

    Button confirm;
    EditText newPass1, newPass2;
    String newPass1_str, newPass2_str, userId;
    User loggedUser;
    SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        //获得id

        sharedViewModel = new ViewModelProvider(Login.login).get(SharedViewModel.class);
        loggedUser = sharedViewModel.getUser();
        init();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passwordValid()) {
                    loggedUser.setPassword(newPass1_str);
                    loggedUser.update(loggedUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(ResetPwd.this, "success", Toast.LENGTH_SHORT).show();
                                Login.actionStart(ResetPwd.this, loggedUser.getUser_name(), loggedUser.getPassword());
                            }else{
                                Toast.makeText(ResetPwd.this, "Network Error, Please check your Internet connection", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    void init() {
        confirm = findViewById(R.id.confirm_res);
        newPass1 = findViewById(R.id.password_res1);
        newPass2 = findViewById(R.id.password_res2);
    }



    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, ResetPwd.class);
        intent.putExtra("param1", data1);
        intent.putExtra("param2", data2);
        context.startActivity(intent);
    }

    private boolean passwordValid() {
        boolean allCorrect = true;
        newPass1_str = newPass1.getText().toString();
        newPass2_str = newPass2.getText().toString();
        if (newPass1_str.length() < 8) {
            newPass1.setError("password too short");
            allCorrect = false;
        }
        if (!newPass1_str.equals(newPass2_str)) {
            newPass2.setError("passwords not match");
            allCorrect = false;
        }
        return allCorrect;
    }
}