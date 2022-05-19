package com.example.habitac.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.habitac.R;
import com.example.habitac.View.WheelView;

public class Me extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shop, container, false);
        WheelView wheelView = root.findViewById(R.id.wv_wheel);
        ImageView avatar = root.findViewById(R.id.iv_play);
        avatar.setOnClickListener(new View.OnClickListener()  {
            public void onClick(View view) {
                wheelView.rotate(7);
            }

        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
