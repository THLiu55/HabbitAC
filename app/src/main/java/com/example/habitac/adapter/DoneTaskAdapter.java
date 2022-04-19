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

public class DoneTaskAdapter extends RecyclerView.Adapter<TaskViewHolder>{
    List<Task> task_done = new ArrayList<>();
    Context context;

    public void setTask_done(List<Task> task_done) {
        this.task_done = task_done;
    }



    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.delete_task_card, parent, false);
        context = parent.getContext();
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.taskName.setText(task_done.get(position).getName());
        holder.taskName.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.item_come_up));
        holder.checked.setChecked(true);
        holder.checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment.complete2todo(task_done.get(position));
            }
        });
        holder.editArea.setClickable(false);
    }

    @Override
    public int getItemCount() {
        return task_done.size();
    }
}
