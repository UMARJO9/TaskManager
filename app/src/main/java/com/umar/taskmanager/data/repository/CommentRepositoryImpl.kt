package com.umar.taskmanager.data.repository

import com.umar.taskmanager.data.local.dao.CommentDao
import com.umar.taskmanager.data.mapper.toDomain
import com.umar.taskmanager.data.mapper.toEntity
import com.umar.taskmanager.domain.model.Comment
import com.umar.taskmanager.domain.repository.CommentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CommentRepositoryImpl(
    private val commentDao: CommentDao
) : CommentRepository {
    override suspend fun addComment(comment: Comment): Long =
        commentDao.insert(comment.toEntity())

    override fun observeComments(taskId: Long): Flow<List<Comment>> =
        commentDao.observeComments(taskId).map { list -> list.map { it.toDomain() } }
}