package com.example.habitac.utils;

import android.annotation.SuppressLint;
<<<<<<< HEAD
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
=======
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
>>>>>>> bcfb4cd (task item add animation~)

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
<<<<<<< HEAD

=======
    Context mContext;
>>>>>>> bcfb4cd (task item add animation~)
    public void setTasks_done(List<TaskDone> tasks_done) {
        this.tasks_done = tasks_done;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.delete_task_card, parent, false);
<<<<<<< HEAD
=======
        mContext = parent.getContext();
>>>>>>> bcfb4cd (task item add animation~)
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.taskName.setText(tasks_done.get(position).getTaskName());
<<<<<<< HEAD
=======
        holder.taskName.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.item_come_up));
>>>>>>> bcfb4cd (task item add animation~)
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
