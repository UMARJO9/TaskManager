package com.umar.taskmanager.domain.repository

import com.umar.taskmanager.domain.model.Comment
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    suspend fun addComment(comment: Comment): Long
    fun observeComments(taskId: Long): Flow<List<Comment>>
}