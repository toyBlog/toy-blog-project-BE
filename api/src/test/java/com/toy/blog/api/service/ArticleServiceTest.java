package com.toy.blog.api.service;

import com.toy.blog.api.exception.article.NotFoundArticleException;
import com.toy.blog.api.model.request.ArticleRequest;
import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.Article;
import com.toy.blog.domain.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("domain-dev")
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Autowired
    ArticleRepository articleRepository;

    @Test
    @DisplayName("게시글 목록 조회")
    public void getArticlesTest() {
        ArticleRequest.Search search = new ArticleRequest.Search();
//        articleService.getArticles(search);
        List<Article> list = articleRepository.getArticleList(search.getPage(), search.getSize());

        for (Article article : list) {
            log.info("article title : {}", article.getTitle());
        }
        assertThat(list.size()).isEqualTo(5);
    }

    @Test
    @DisplayName("게시글 상세 보기")
    public void getArticleTest() {
        Article article = articleRepository.findById(1L).orElseThrow(NotFoundArticleException::new);

        log.info("article.getId() : {}", article.getId());
        log.info("article.getTitle() : {}", article.getTitle());
        log.info("article.getContent() : {}", article.getContent());
    }

    @Test
    @DisplayName("조회수 증가")
    @Transactional
    public void updateViewCountTest() {
        articleRepository.updateViewCount(1L);

        log.info("조회수 : {}", articleRepository.findById(1L).orElseThrow(NotFoundArticleException::new).getViewCount());
        assertThat(articleRepository.findById(1L).orElseThrow(NotFoundArticleException::new).getViewCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("게시글 작성")
    @Transactional
    public void insertArticleTest() {
        ArticleRequest.Register request = new ArticleRequest.Register();
        String testTitle = "insertTest title";
        String testContent = "insertArticleTest content";
        request.setTitle(testTitle);
        request.setContent(testContent);
        Article save = articleRepository.save(request.toEntity(1L));

        log.info("게시글 번호 : {} ", save.getId());
        log.info("게시글 제목 : {}", save.getTitle());
        log.info("게시글 내용 : {}", save.getContent());
        assertThat(save.getId()).isEqualTo(6L);
        assertThat(save.getTitle()).isEqualTo(testTitle);
        assertThat(save.getContent()).isEqualTo(testContent);
    }

    @Test
    @DisplayName("게시글 수정")
    @Transactional
    public void editArticleTest() {
        ArticleRequest.Register request = new ArticleRequest.Register();
        String testTitle = "edit title";
        String testContent = "editArticleTest content";
        request.setTitle(testTitle);
        request.setContent(testContent);
        articleRepository.editArticle(1L, request.getTitle(), request.getContent());

        Article edit = articleRepository.findById(1L).orElseThrow(NotFoundArticleException::new);
        log.info("수정된 제목 : {}", edit.getTitle());
        log.info("수정된 내용 : {}", edit.getContent());
        assertThat(edit.getTitle()).isEqualTo(testTitle);
        assertThat(edit.getContent()).isEqualTo(testContent);
    }

    @Test
    @DisplayName("게시글 삭제")
    @Transactional
    public void deleteArticle() {
        articleRepository.deleteArticle(1L);
        Article delete = articleRepository.findById(1L).orElseThrow(NotFoundArticleException::new);
        log.info("게시글 상태 : {}", delete.getStatus());
        assertThat(delete.getStatus()).isEqualTo(Status.Article.INACTIVE);
    }

}