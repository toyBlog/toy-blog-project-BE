package com.toy.blog.domain.repository.custom;


import com.toy.blog.domain.entity.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleCustomRepository {

    List<Article> findAllArticleBySearch(String title, String content, String nickname, Integer page, Integer size);

    List<Article> findAllFollowedArticleBySearch(String title, String content, String nickname, Integer page, Integer size);

    Optional<Article> findArticleWithUserBy(Long id);

    void updateViewCount(Long id);

    void editArticle(Long id, Long userId, String title, String content);

    void deleteArticle(Long id);
}
