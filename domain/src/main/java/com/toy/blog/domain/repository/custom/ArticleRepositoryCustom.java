package com.toy.blog.domain.repository.custom;

import com.toy.blog.domain.entity.Article;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ArticleRepositoryCustom {

    List<Article> findArticleList(Integer page, Integer size);

    List<Article> findFollowArticleList(List<Long> friendIdList, Pageable pageable);

    long findFollowArticleListTotal(List<Long> friendIdList);

    Optional<Article> findArticleById(Long id);

    void updateViewCount(Long id);

    void updateArticle(Long id, String title, String content);

    void inactiveArticle(Long id);

    void updateLikedCount(Long id, Integer value);

}
