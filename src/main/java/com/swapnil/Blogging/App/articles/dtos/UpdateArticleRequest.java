package com.swapnil.Blogging.App.articles.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
public class UpdateArticleRequest {

    private String title;
    private String body;
    private String subTitle;
}
