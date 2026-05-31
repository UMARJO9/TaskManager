package com.umar.taskmanager.domain.usecase.comment

import com.umar.taskmanager.domain.model.Comment
import com.umar.taskmanager.domain.repository.CommentRepository
import kotlinx.coroutines.flow.Flow

class ObserveCommentsUseCase(
    private val repository: CommentRepository
) {
    operator fun invoke(taskId: Long): Flow<List<Comment>> {
        return repository.observeComments(taskId)
    }
}