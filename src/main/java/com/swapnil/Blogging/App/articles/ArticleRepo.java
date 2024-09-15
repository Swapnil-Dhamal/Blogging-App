package com.swapnil.Blogging.App.articles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepo extends JpaRepository<ArticleEntity, Long> {
    List<ArticleEntity> findAll();

    Object findBySlug(String slug);
}
