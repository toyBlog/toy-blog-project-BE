package com.toy.blog.domain.repository.article;


import com.toy.blog.domain.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> , ArticleRepositoryCustom {
}
