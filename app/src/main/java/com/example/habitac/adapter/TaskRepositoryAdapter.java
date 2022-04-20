package com.example.habitac.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitac.R;
import com.example.habitac.database.Task;
import com.example.habitac.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class TaskRepositoryAdapter extends RecyclerView.Adapter<RepositoryViewHolder>{
    List<Task> allTask = new ArrayList<>();
    Context context;

    public void setAllTask(List<Task> allTask) {
        this.allTask = allTask;
    }

    @NonNull
    @Override
    public RepositoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.repository_task_card, parent, false);
        context = parent.getContext();
        Log.d("heeee", "here");
        return new RepositoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoryViewHolder holder, int position) {
        holder.taskName.setText(allTask.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return allTask.size();
    }
}
