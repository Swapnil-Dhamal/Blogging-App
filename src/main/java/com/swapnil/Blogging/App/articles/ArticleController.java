package com.swapnil.Blogging.App.articles;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @GetMapping("/getArticles")
    public String getArticles(){
        return "Articles";
    }
}
