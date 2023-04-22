package com.toy.blog.domain.repository.custom;


import com.toy.blog.domain.entity.Article;
import com.toy.blog.domain.entity.Article;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface ArticleCustomRepository {

    Optional<Article> findArticleWithUserBy(Long id);

    List<Article> findArticleListByOrderByIdDesc(Integer page, Integer size);

    List<Article> findArticlebySearchColum(String title, String content,String nickname,Integer page, Integer size) ;



    void updateViewCount(Long id);

    long editArticle(@NotNull Long id,
                   @NotNull Long userId,
                   @NotNull String title,
                   @NotNull String content);

    long deleteArticle(Long id);
}
