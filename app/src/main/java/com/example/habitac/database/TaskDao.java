package com.example.habitac.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insertTask(Task ... tasks);

    @Delete
    void deleteTask(Task ... tasks);

    @Update
    void updateTask(Task ... tasks);

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getAllTasks();

    @Query("SELECT * FROM Task WHERE frequency & :today != 0 AND isDone = 1")
    LiveData<List<Task>> getALlDoneTask(int today);

    @Query("SELECT * FROM Task WHERE frequency & :today != 0 AND isDone = 0")
    LiveData<List<Task>> getALlTodoTask(int today);

    @Insert
    void insertTaskHistory(TaskHistory ... TaskHistorys);

    @Delete
    void deleteTaskHistory(TaskHistory ... TaskHistorys);


    @Query("SELECT * FROM TaskHistory WHERE date = :theDay AND isDone = 1")
    LiveData<List<TaskHistory>> getDoneHistoryOf(String theDay);

    @Query("SELECT * FROM TaskHistory WHERE date = :theDay AND isDone = 0")
    LiveData<List<TaskHistory>> getNotDoneHistoryOf(String theDay);

    @Query("SELECT * FROM TASKHISTORY WHERE date LIKE :month || '%' AND isDone = 0")
    LiveData<List<TaskHistory>> getNotDoneHistoryOfMonth(String month);

    @Query("SELECT * FROM TASKHISTORY WHERE date LIKE :month || '%' AND isDone = 1")
    LiveData<List<TaskHistory>> getDoneHistoryOfMonth(String month);

    @Query("SELECT * FROM TaskHistory WHERE date LIKE :month")
    LiveData<List<TaskHistory>> getAllHistoryOfMonth(String month);
}
