package com.umar.taskmanager.domain.usecase.comment

import com.umar.taskmanager.domain.model.Comment
import com.umar.taskmanager.domain.repository.CommentRepository

class AddCommentUseCase(
    private val repository: CommentRepository
) {
    suspend operator fun invoke(comment: Comment): Long {
        require(comment.text.isNotBlank()) { "Коммент не должен быть пустым" }
        return repository.addComment(comment)
    }
}