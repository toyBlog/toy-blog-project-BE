package com.toy.blog.domain.repository;

import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.Article;
import com.toy.blog.domain.entity.Liked;
import com.toy.blog.domain.entity.User;
import com.toy.blog.domain.repository.custom.LikedRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikedRepository extends JpaRepository<Liked, Long>, LikedRepositoryCustom {

    boolean existsByUserAndAndArticleAndStatus(User user, Article article, Status.Like status);

    Optional<Liked> findByUserAndArticle(User user, Article article);
}
