package com.example.habitac.utils;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitac.R;
import com.example.habitac.database.TaskDone;
import com.example.habitac.database.TasksDao;
import com.example.habitac.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DoneTaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    List<TaskDone> tasks_done = new ArrayList<>();

    public void setTasks_done(List<TaskDone> tasks_done) {
        this.tasks_done = tasks_done;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.delete_task_card, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.taskName.setText(tasks_done.get(position).getTaskName());
        holder.checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExecutorService service = Executors.newSingleThreadExecutor();
                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        HomeFragment.complete_to_todo(tasks_done.get(position));
                    }
                });
            }
        });
        holder.editArea.setClickable(false);
    }

    @Override
    public int getItemCount() {
        return tasks_done.size();
    }
}
