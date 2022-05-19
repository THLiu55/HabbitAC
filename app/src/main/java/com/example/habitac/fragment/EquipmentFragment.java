package com.example.habitac.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.habitac.R;
import com.example.habitac.activity.Login;
import com.example.habitac.database.Item;
import com.example.habitac.database.User;
import com.example.habitac.model.SharedViewModel;

import java.util.List;

import cn.bmob.v3.*;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class EquipmentFragment extends Fragment {
    private SharedViewModel sharedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sharedViewModel = new ViewModelProvider(Login.login).get(SharedViewModel.class);
        String UserObjectId = sharedViewModel.getUser().getObjectId();
        BmobQuery<Item> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("own", UserObjectId);
//        bmobQuery.findObjects(new FindListener<Item>() {
//            @Override
//            public void done(List<Item> list, BmobException e) {
//
//            }
//        })




        return inflater.inflate(R.layout.fragment_equipment, container, false);


    }
}
