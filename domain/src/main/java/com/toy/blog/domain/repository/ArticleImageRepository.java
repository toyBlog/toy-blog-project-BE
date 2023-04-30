package com.toy.blog.domain.repository;

import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.Article;
import com.toy.blog.domain.entity.ArticleImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleImageRepository extends JpaRepository<ArticleImage, Long> {

    List<ArticleImage> findByArticleAndStatus(Article article, Status.ArticleImage status);


    @Modifying
    @Query("update ArticleImage ai set ai.status=:status where ai.article.id=:articleId")
    int updateStatusByArticleId(@Param("status") Status.ArticleImage status, @Param("articleId") Long articleId);

}
