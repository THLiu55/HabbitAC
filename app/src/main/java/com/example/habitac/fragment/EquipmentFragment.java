package com.example.habitac.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.habitac.R;
import com.example.habitac.activity.Login;
import com.example.habitac.database.User;
import com.example.habitac.model.SharedViewModel;

import cn.bmob.v3.*;

public class EquipmentFragment extends Fragment {
    SharedViewModel sharedViewModel;
    User loggedUser;



    private RecyclerView recyclerView_equipment;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_equipment, container, false);

        sharedViewModel = new ViewModelProvider(Login.login).get(SharedViewModel.class);
        loggedUser = sharedViewModel.getUser();



        return root;
    }
}
