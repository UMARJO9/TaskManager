package com.umar.taskmanager.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.umar.taskmanager.data.local.entity.TaskEntity

@Dao
interface TaskDao{

    @Insert
    suspend fun insert(task: TaskEntity):Long

    @Update
    suspend fun update(task: TaskEntity)

    @Delete
    suspend fun delete(task: TaskEntity)
}