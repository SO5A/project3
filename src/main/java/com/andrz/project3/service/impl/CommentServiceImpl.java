package com.andrz.project3.service.impl;

import com.andrz.project3.entity.Comment;
import com.andrz.project3.exception.NotFoundException;
import com.andrz.project3.repository.CommentRepository;
import com.andrz.project3.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    final CommentRepository commentRepository;
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment findCommentById(Long commentId) {
        return commentRepository.findById(String.valueOf(commentId))
                .orElseThrow(() -> new NotFoundException(String.format("Employee not found with ID %d", commentId)));
    }

    @Override
    public void deleteComment(Long commentId) {
        var comment = commentRepository.findById(String.valueOf(commentId))
                .orElseThrow(() -> new NotFoundException(String.format("Employee not found with ID %d", commentId)));

        commentRepository.deleteById(String.valueOf(comment.getId()));
    }

    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }
}
