package com.example.habitac.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.habitac.R;

public class ContactUs extends AppCompatActivity{

    ActionBarDrawerToggle actionBarDrawerToggle;

    EditText defaultEmail,userSubject,userContent;
    Button sendBtn;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        Toolbar toolbar = findViewById(R.id.contact_toolBar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        userContent = (EditText) findViewById(R.id.user_content);
        sendBtn = (Button) findViewById(R.id.send_email_btn);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String content = userContent.getText().toString();
               sendEmail(content);

            }
        });
    }

    private void sendEmail(String content) {
        Intent emailIntent = new Intent(Intent.ACTION_VIEW);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_EMAIL,"980734038@qq.com");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"habitAC feedback");
        emailIntent.putExtra(Intent.EXTRA_TEXT,content);

//        startActivity(Intent.createChooser(emailIntent,"Choose"));

        if(emailIntent.resolveActivity(getPackageManager())!=null){
            startActivity(emailIntent);
        }else {
            Toast.makeText(ContactUs.this,"Please download the email APP first",Toast.LENGTH_SHORT).show();
        }


    }

}







