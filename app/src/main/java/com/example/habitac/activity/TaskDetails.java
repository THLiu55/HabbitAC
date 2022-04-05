package com.example.habitac.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;
import androidx.room.Insert;

import com.example.habitac.R;
import com.example.habitac.database.Task;
import com.example.habitac.database.TaskDao;
import com.example.habitac.database.TaskDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskDetails extends AppCompatActivity {
    EditText editText_taskName;
    Button confirm_edit;
    boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isEdit = false;

        setContentView(R.layout.activity_task_details);
        editText_taskName = findViewById(R.id.habitTitle_edit);
        confirm_edit = findViewById(R.id.confirm_edit);
        confirm_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText_taskName.getText().toString();
                ExecutorService service = Executors.newSingleThreadExecutor();
                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        TaskDatabase taskDatabase = TaskDatabase.getDatabase(getApplicationContext());
                        TaskDao dao = taskDatabase.getDao();
                        Task task = new Task();
                        task.setTaskName(name);
                        dao.insert(task);
                    }
                });
                Main.actionStart(TaskDetails.this, null, null);
            }
        });

    }



}