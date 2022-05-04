package com.example.habitac.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitac.R;
import com.example.habitac.activity.TaskDetails;
import com.example.habitac.database.Task;
import com.example.habitac.database.TaskDao;
import com.example.habitac.database.TaskDatabase;
import com.example.habitac.fragment.HomeFragment;
import com.example.habitac.fragment.TaskRepositoryFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        return new RepositoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.taskName.setText(allTask.get(position).getName());
        holder.clickArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TaskDetails.class);
                intent.putExtra("id", String.valueOf(allTask.get(position).getId()));
                intent.putExtra("name", allTask.get(position).getName());
                intent.putExtra("keeping", allTask.get(position).getRemainDays());
                intent.putExtra("classify", allTask.get(position).getClassification());
                intent.putExtra("alarmHour", allTask.get(position).getRemindHour());
                intent.putExtra("alarmMin", allTask.get(position).getRemindMin());
                context.startActivity(intent);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context).setTitle("Confirm").setTitle("are you going to delete habit '" + allTask.get(position).getName() + "'")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TaskDao dao = TaskDatabase.getDatabase(context).getDao();
                        ExecutorService service = Executors.newSingleThreadExecutor();
                        Task deleteTask = new Task();
                        deleteTask.setId(allTask.get(position).getId());
                        service.execute(new Runnable() {
                            @Override
                            public void run() {
                                dao.deleteTask(deleteTask);
                            }
                        });
                    }
                })
                        .setNegativeButton("no", null).show();
                Log.d("delete", "here");
            }
        });
    }

    @Override
    public int getItemCount() {
        return allTask.size();
    }
}
