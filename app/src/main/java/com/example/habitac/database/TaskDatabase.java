package com.example.habitac.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class, TaskHistory.class}, version = 1, exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase{
    private static TaskDatabase INSTANCE;

    public static synchronized TaskDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            // 使用getApplicationContext() 保证返回节点是全局唯一的，防止内存泄露
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    TaskDatabase.class, "task database").build();
        }
        return INSTANCE;
    }

    public abstract TaskDao getDao();
}
