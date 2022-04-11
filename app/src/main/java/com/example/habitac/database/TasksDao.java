package com.example.habitac.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TasksDao {
    @Insert
    void insertTodo(TaskTodo ... taskTodos);

    @Delete
    void deleteTodo(TaskTodo ... taskTodos);

    @Query("SELECT * FROM TaskTodo ORDER BY ID")
    LiveData<List<TaskTodo>> getALlTodo();

    @Insert
    void insertDone(TaskDone ... taskDones);

    @Delete
    void deleteDone(TaskDone ... taskDones);

    @Query("SELECT * FROM TaskDone ORDER BY ID")
    LiveData<List<TaskDone>> getAllDone();

    @Query("SELECT * FROM TASKTODO WHERE ID = :task_id")
    TaskTodo getToDo(int task_id);
}
