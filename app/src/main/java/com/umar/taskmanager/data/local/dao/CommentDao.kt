package com.umar.taskmanager.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.umar.taskmanager.data.local.entity.CommentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {
    @Insert
    suspend fun insert(comment: CommentEntity): Long

    @Query("SELECT * FROM comments WHERE taskId=:taskId ORDER BY createdAt ASC")
    fun observeComments(taskId: Long): Flow<List<CommentEntity>>
}