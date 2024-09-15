package com.swapnil.Blogging.App.comments;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article/{article-slug}/comments")
public class CommentController {

    @GetMapping
    public String getComment(){
        return "Comments";
    }
}
