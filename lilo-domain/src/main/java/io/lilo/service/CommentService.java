package io.lilo.service;

import io.lilo.domain.Comment;
import io.lilo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    SessionService sessionService;

    public Comment create(Comment comment) {

        Assert.notNull(comment);

        comment.setAuthor(sessionService.getCurrentUser());

        //TODO articleId Check
        if (!comment.canRegistable())
            throw new IllegalArgumentException();

        return commentRepository.save(comment);
    }
}