package com.example.habitac.utils;

import android.annotation.SuppressLint;
<<<<<<< HEAD
=======
import android.content.Context;
>>>>>>> bcfb4cd (task item add animation~)
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
=======
import android.view.animation.AnimationUtils;
>>>>>>> bcfb4cd (task item add animation~)
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitac.R;
import com.example.habitac.database.TaskDone;
import com.example.habitac.database.TaskTodo;
import com.example.habitac.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TodoTaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
<<<<<<< HEAD
=======
    Context mContext;
>>>>>>> bcfb4cd (task item add animation~)
    List<TaskTodo> tasks_todo = new ArrayList<>();
    public void setTasks_todo(List<TaskTodo> tasks_todo) {
        this.tasks_todo = tasks_todo;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
<<<<<<< HEAD
=======
        mContext = parent.getContext();
>>>>>>> bcfb4cd (task item add animation~)
        View view = layoutInflater.inflate(R.layout.task_card, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.taskName.setText(tasks_todo.get(position).getTaskName());
<<<<<<< HEAD
=======
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.item_come_up));
>>>>>>> bcfb4cd (task item add animation~)
        holder.checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExecutorService service = Executors.newSingleThreadExecutor();
                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Tag", "hello");
                        HomeFragment.todo_to_complete(tasks_todo.get(position).getId());
                    }
                });
            }
        });
        holder.editArea.setClickable(true);
        holder.editArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks_todo.size();
    }
}
