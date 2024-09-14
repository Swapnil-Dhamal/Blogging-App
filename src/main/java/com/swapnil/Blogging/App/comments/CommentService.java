package com.swapnil.Blogging.App.comments;

import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private CommentRepo commentRepo;

    public CommentService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }
}
