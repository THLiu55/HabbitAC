package com.example.habitac.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.habitac.R;
import com.example.habitac.activity.Main;
import com.example.habitac.utils.AvatarGetter;
import com.example.habitac.utils.ItemAdapter;



public class HomeFragment extends Fragment {

    RecyclerView recyclerView; // 滚动组件的 instance
    String[] s1, s2;  // 文本数据的 instance
    Context context;
    int[] images = {R.drawable.item_pic, R.drawable.item_pic, R.drawable.item_pic,
            R.drawable.item_pic, R.drawable.item_pic, R.drawable.item_pic,
            R.drawable.item_pic, R.drawable.item_pic, R.drawable.item_pic};  // 照片数据的 instance

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView avatar = root.findViewById(R.id.imageView);
        Button refreshAvatar = root.findViewById(R.id.getAvatar);

        refreshAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("BUTTON", "Detected");
                AvatarGetter ag = new AvatarGetter();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Thread", "Created");
                        Bitmap ava = ag.getAvatar("test");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                avatar.setImageBitmap(ava);
                            }
                        });
                    }
                }).start();
            }
        });

        s1 = getResources().getStringArray(R.array.habit_name);
        s2 = getResources().getStringArray(R.array.description);
        recyclerView = root.findViewById(R.id.recycle_view);
        context = getActivity();
        // 构建 adapter
        ItemAdapter myAdapter = new ItemAdapter(context, s1, s2, images);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        return root;
    }
}