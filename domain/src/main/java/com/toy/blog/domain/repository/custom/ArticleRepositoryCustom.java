package com.toy.blog.domain.repository.custom;

import com.toy.blog.domain.entity.Article;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleRepositoryCustom {

    List<Article> findArticleList(Integer page, Integer size);

    List<Article> findFollowArticleList(List<Long> friendIdList, Pageable pageable);

<<<<<<< HEAD
    long countFollowArticleList(List<Long> friendIdList);
=======
    long findFollowArticleListTotal(List<Long> friendIdList);
>>>>>>> 8c064ad24a93639299452f8b9268c23978a8290b

    Optional<Article> findArticleById(Long id);

    void updateViewCount(Long id);

    void updateArticle(Long id, String title, String content);

    void inactiveArticle(Long id);

    void updateLikedCount(Long id, Integer value);

<<<<<<< HEAD
    long countByTitleOrContent(String keyword);
=======
>>>>>>> 8c064ad24a93639299452f8b9268c23978a8290b
}
