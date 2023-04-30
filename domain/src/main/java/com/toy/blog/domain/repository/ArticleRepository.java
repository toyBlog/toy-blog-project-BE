package com.toy.blog.domain.repository;

import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.Article;
import com.toy.blog.domain.repository.custom.ArticleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom {


    Optional<Article> findByIdAndStatus(Long id, Status.Article status);

    boolean existsByIdAndStatus(Long id, Status.Article status);

    @Query("select a from Article a join fetch a.user where a.id=:id and a.status=:status")
    Optional<Article> findByIdAndStatusWithUser(@Param("id") Long id, @Param("status") Status.Article status);

}
