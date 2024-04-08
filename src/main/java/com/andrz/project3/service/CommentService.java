package com.andrz.project3.service;

import com.andrz.project3.entity.Comment;
import com.andrz.project3.entity.Task;
import com.andrz.project3.entity.User;

public interface CommentService {
    public Comment findCommentById(Long commentId);
    public void deleteComment(Long commentId);
    public Comment createComment(Comment comment);
    void addCommentToTask(Task task, String newCommentText, User user);
}
