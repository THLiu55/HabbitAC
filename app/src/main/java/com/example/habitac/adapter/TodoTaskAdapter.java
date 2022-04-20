package com.example.habitac.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitac.R;
import com.example.habitac.database.Task;
import com.example.habitac.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class TodoTaskAdapter extends RecyclerView.Adapter<TaskViewHolder>{
    List<Task> task_todo = new ArrayList<>();
    Context context;

    public void setTask_todo(List<Task> task_todo) {
        this.task_todo = task_todo;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.task_card, parent, false);
        context = parent.getContext();
        return new TaskViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.taskName.setText(task_todo.get(position).getName());
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.item_come_up));
        holder.checked.setChecked(false);
        holder.checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment.todo2complete(task_todo.get(position));
            }
        });
        holder.editArea.setClickable(false);
        switch (task_todo.get(position).getClassification()) {
            case 0:
                holder.classifyBar.setBackground(context.getDrawable(R.drawable.task_red_bar));
                break;
            case 1:
                holder.classifyBar.setBackground(context.getDrawable(R.drawable.task_blue_bar));
                break;
            case 2:
                holder.classifyBar.setBackground(context.getDrawable(R.drawable.task_yello_bar));
                break;
            case 3:
                holder.classifyBar.setBackground(context.getDrawable(R.drawable.task_purple_bar));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return task_todo.size();
    }
}
