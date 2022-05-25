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

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class ResetName extends AppCompatActivity {

    Button confirm;
    EditText newName1, newName2;
    String newName1_str, newName2_Str;
    User loggedUser;
    SharedViewModel sharedViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_name);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        sharedViewModel = new ViewModelProvider(Login.login).get(SharedViewModel.class);
        loggedUser = sharedViewModel.getUser();
        init();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newName1_str = newName1.getText().toString();
                newName2_Str = newName2.getText().toString();
                if (!newName1_str.equals(newName2_Str)) return;
                BmobQuery<User> bmobQuery = new BmobQuery<>();
                bmobQuery.addWhereEqualTo("user_name", newName1_str);
                bmobQuery.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if (e == null) {
                            if (list.size() != 0) {
                                newName1.setError("This name already existed");
                            }else{
                                loggedUser.setUser_name(newName1_str);
                                loggedUser.update(loggedUser.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            Toast.makeText(ResetName.this, "Name changed success", Toast.LENGTH_SHORT).show();
                                            Login.actionStart(ResetName.this, loggedUser.getUser_name(), loggedUser.getPassword());
                                        }else{
                                            Toast.makeText(ResetName.this, "Network Error, Please check your Internet connection", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
            }
        });
    }

    void init() {
        confirm = findViewById(R.id.confirm_resname);
        newName1 = findViewById(R.id.name_res1);
        newName2 = findViewById(R.id.name_res2);
    }

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, ResetName.class);
        intent.putExtra("param1", data1);
        intent.putExtra("param2", data2);
        context.startActivity(intent);
    }
}