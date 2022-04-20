package com.example.habitac.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.habitac.R;
import com.example.habitac.adapter.TaskRepositoryAdapter;
import com.example.habitac.database.Task;
import com.example.habitac.database.TaskDao;
import com.example.habitac.database.TaskDatabase;

import java.util.List;

public class TaskRepositoryFragment extends DialogFragment {
    private RecyclerView recyclerView;
    private LiveData<List<Task>> taskLiveData;
    private TaskRepositoryAdapter taskRepositoryAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_task_repository, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Log.d("hereeeee", "hhhhh");
        taskRepositoryAdapter = new TaskRepositoryAdapter();
        recyclerView = root.findViewById(R.id.recycle_task_repository);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(taskRepositoryAdapter);


        TaskDatabase database = TaskDatabase.getDatabase(getContext());
        TaskDao dao = database.getDao();


        taskLiveData = dao.getAllTasks();

        taskLiveData.observe(requireActivity(), new Observer<List<Task>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Task> tasks) {
                Log.d("heeee", String.valueOf(tasks.size()));
                taskRepositoryAdapter.setAllTask(tasks);
                taskRepositoryAdapter.notifyDataSetChanged();
            }
        });

        return root;
    }
}