package com.example.aman.whatsapp;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insertTask(Task task);


    @Query("SELECT * FROM task_table")
    LiveData<List<Task>> getAllTasks();


}
