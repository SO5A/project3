package com.andrz.project3.service;

import com.andrz.project3.entity.Comment;

public interface CommentService {
    public Comment findCommentById(Long commentId);
    public void deleteComment(Long commentId);
    public Comment createComment(Comment comment);
}
