package com.example.habitac.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.habitac.R;
import com.example.habitac.database.Task;
import com.example.habitac.database.TaskHistory;
import com.example.habitac.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class CalendarTodoAdapter extends RecyclerView.Adapter<TaskViewHolder>{
    Context context;
    List<TaskHistory> taskHistories = new ArrayList<>();

    public void setTaskHistories(List<TaskHistory> taskHistories) {
        this.taskHistories = taskHistories;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.task_card, parent, false);
        context = parent.getContext();
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.item_come_up));
        holder.taskName.setText(taskHistories.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return taskHistories.size();
    }
}