package com.toy.blog.domain.repository;

import com.toy.blog.domain.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("domain-dev")
public class ArticleRepositoryTest {

    @Autowired
    ArticleRepository articleRepository;

    @Test
    @DisplayName("UserId로 게시글 찾기")
    public void findArticleByUserIdTest() {
        Article article = articleRepository.findById(1L).orElseThrow();
        log.info("articleId : {}", article.getId());
        log.info("articleId : {}", article.getTitle());
        assertThat(article.getId()).isEqualTo(1L);
        assertThat(article.getTitle()).isEqualTo("제목1");
    }

    @Test
    @DisplayName("게시글 조회 + 댓글 조회")
    public void findArticleWithCommentByIdTest() {
        Long articleId = 1L;

        Article article = articleRepository.findArticleWithCommentsById(articleId).get();

        Assertions.assertEquals(2, article.getCommentList().size());
    }

}