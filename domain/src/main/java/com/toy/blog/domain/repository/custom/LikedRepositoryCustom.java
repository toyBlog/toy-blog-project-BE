package com.toy.blog.domain.repository.custom;

import com.toy.blog.domain.entity.Article;
import com.toy.blog.domain.entity.Liked;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LikedRepositoryCustom {

    Optional<Liked> findByArticleAndUser(Long id, Long userId);

    void deleteLiked(Long id);

    List<Article> findArticleListByUserId(Long userId, Pageable pageable);

    long countArticleListByUserId(Long userId);

}