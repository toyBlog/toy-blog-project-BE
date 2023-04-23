package com.toy.blog.domain.repository.custom;

import com.toy.blog.domain.entity.Article;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleCustomRepository {

    List<Article> getArticleList(Integer page, Integer size);

    void updateViewCount(Long id);

    void editArticle(Long id, String title, String content);

    void deleteArticle(Long id);

    List<Article> getFollowArticleList(List<Long> friendIdList, Pageable pageable);
}
