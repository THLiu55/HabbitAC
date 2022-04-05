package com.example.habitac.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insert(Task ... tasks);

    @Delete
    void delete(Task ... tasks);

    @Query("SELECT * FROM Task ORDER BY ID DESC")
    LiveData<List<Task>> getAllLive();
}
