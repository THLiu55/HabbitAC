package com.example.habitac.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {TaskTodo.class, TaskDone.class}, version = 1, exportSchema = false)
public abstract class TasksDatabase extends RoomDatabase{
    private static TasksDatabase INSTANCE;

    public static synchronized TasksDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            // 使用getApplicationContext() 保证返回节点是全局唯一的，防止内存泄露
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    TasksDatabase.class, "tasks database").build();
        }
        return INSTANCE;
    }

    public abstract TasksDao getDao();
}
