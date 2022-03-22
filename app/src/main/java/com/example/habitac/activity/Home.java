package com.example.habitac.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.habitac.R;
import com.example.habitac.utils.ItemAdapter;

public class Home extends BasicActivity {
    RecyclerView recyclerView; // 滚动组件的 instance
    String[] s1, s2;  // 文本数据的 instance
    int[] images = {R.drawable.item_pic, R.drawable.item_pic, R.drawable.item_pic,
            R.drawable.item_pic, R.drawable.item_pic, R.drawable.item_pic,
            R.drawable.item_pic, R.drawable.item_pic, R.drawable.item_pic};  // 照片数据的 instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // 将所有资源在 activity 中初始化 （找到索引）
        s1 = getResources().getStringArray(R.array.habit_name);
        s2 = getResources().getStringArray(R.array.description);
        recyclerView = findViewById(R.id.recycle_view);
        // 构建 adapter
        ItemAdapter myAdapter = new ItemAdapter(this, s1, s2, images);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }



    // 进入本页面请调用：
    // 调用格式：目标页.actionStart(起点，携带数据1， 携带数据2);
    public static void actionStart(Context context, String data, String data2) {
        Intent intent = new Intent(context, Home.class);
        intent.putExtra("param1", data);
        intent.putExtra("param2", data);
        context.startActivity(intent);
    }
}