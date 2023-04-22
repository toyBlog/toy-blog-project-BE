package com.toy.blog.api.service;

import com.toy.blog.api.model.request.ArticleRequest;
import com.toy.blog.domain.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Autowired
    ArticleRepository articleRepository;

    @Test
    @DisplayName("게시글 목록 조회")
    public void getArticlesTest() {
        ArticleRequest.Search search = new ArticleRequest.Search();
        articleService.getArticles(search);
    }

}