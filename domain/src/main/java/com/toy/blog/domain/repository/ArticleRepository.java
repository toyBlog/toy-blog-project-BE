package com.toy.blog.domain.repository;



import com.toy.blog.domain.entity.Article;
import com.toy.blog.domain.repository.custom.ArticleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> , ArticleRepositoryCustom {

}
