package com.toy.blog.domain.repository;



import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.Article;
import com.toy.blog.domain.repository.custom.ArticleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom {

    Optional<Article> findByIdAndStatus(Long id, Status.Article status);
}
