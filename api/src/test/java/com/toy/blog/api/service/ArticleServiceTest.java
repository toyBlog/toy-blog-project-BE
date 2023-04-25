package com.toy.blog.api.service;

import com.toy.blog.api.exception.article.NotFoundArticleException;
import com.toy.blog.api.model.request.ArticleRequest;
import com.toy.blog.api.model.request.LikedRequest;
import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.Article;
import com.toy.blog.domain.entity.Liked;
import com.toy.blog.domain.repository.ArticleRepository;
import com.toy.blog.domain.repository.LikedRepository;
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

    @Autowired
    LikedRepository likedRepository;

    @Test
    @DisplayName("게시글 목록 조회")
    public void getArticlesTest() {
        ArticleRequest.Search search = new ArticleRequest.Search();
//        articleService.getArticles(search);
        List<Article> list = articleRepository.findByTitleOrContent("" ,search.getPage(), search.getSize());

        for (Article article : list) {
            log.info("article title : {}", article.getTitle());
        }
        assertThat(list.size()).isEqualTo(5);
    }

    @Test
    @DisplayName("게시글 상세 보기")
    public void getArticleTest() {
        Article article = articleRepository.findByIdAndStatus(1L, Status.Article.ACTIVE).orElseThrow(NotFoundArticleException::new);

        log.info("article.getId() : {}", article.getId());
        log.info("article.getTitle() : {}", article.getTitle());
        log.info("article.getContent() : {}", article.getContent());
    }

//    @Test
//    @DisplayName("조회수 증가")
//    @Transactional
//    public void updateViewCountTest() {
//        articleRepository.updateViewCount(1L);
//
//        log.info("조회수 : {}", articleRepository.findById(1L).orElseThrow(NotFoundArticleException::new).getViewCount());
//        assertThat(articleRepository.findById(1L).orElseThrow(NotFoundArticleException::new).getViewCount()).isEqualTo(1);
//    }

//    @Test
//    @DisplayName("게시글 작성")
//    @Transactional
//    public void insertArticleTest() {
//        ArticleRequest.Register request = new ArticleRequest.Register();
//        String testTitle = "insertTest title";
//        String testContent = "insertArticleTest content";
//        request.setTitle(testTitle);
//        request.setContent(testContent);
//        Article save = articleRepository.save(request.toEntity(1L));
//
//        log.info("게시글 번호 : {} ", save.getId());
//        log.info("게시글 제목 : {}", save.getTitle());
//        log.info("게시글 내용 : {}", save.getContent());
//        assertThat(save.getId()).isEqualTo(6L);
//        assertThat(save.getTitle()).isEqualTo(testTitle);
//        assertThat(save.getContent()).isEqualTo(testContent);
//    }

//    @Test
//    @DisplayName("게시글 수정")
//    @Transactional
//    public void editArticleTest() {
//        ArticleRequest.Register request = new ArticleRequest.Register();
//        String testTitle = "edit title";
//        String testContent = "editArticleTest content";
//        request.setTitle(testTitle);
//        request.setContent(testContent);
//        articleRepository.updateArticle(1L, request.getTitle(), request.getContent());
//
//        Article edit = articleRepository.findArticleById(1L).orElseThrow(NotFoundArticleException::new);
//        log.info("수정된 제목 : {}", edit.getTitle());
//        log.info("수정된 내용 : {}", edit.getContent());
//        assertThat(edit.getTitle()).isEqualTo(testTitle);
//        assertThat(edit.getContent()).isEqualTo(testContent);
//    }

//    @Test
//    @DisplayName("게시글 삭제")
//    @Transactional
//    public void deleteArticleTest() {
//        articleRepository.inactiveArticle(1L);
//        Article delete = articleRepository.findById(1L).orElseThrow(NotFoundArticleException::new);
//        log.info("게시글 상태 : {}", delete.getStatus());
//        assertThat(delete.getStatus()).isEqualTo(Status.Article.INACTIVE);
//    }

//    @Test
//    @DisplayName("좋아요 증가")
//    @Transactional
//    public void increaseLikedCount() {
//        articleRepository.updateLikedCount(1L, 1);
//        Article article = articleRepository.findArticleById(1L).orElseThrow(NotFoundArticleException::new);
//        log.info("게시글 좋아요 : {}", article.getLikedCount());
//        assertThat(article.getLikedCount()).isEqualTo(1);
//    }

    @Test
    @DisplayName("좋아요 테이블 생성")
    @Transactional
    public void createLikedTableTest() {
        likedRepository.save(new LikedRequest.Register().toEntity(1L, 1L));
        Liked liked = likedRepository.findByArticleAndUser(1L, 1L).orElseThrow();
        log.info("게시글 id : {}", liked.getArticle().getId());
        log.info("사용자 id : {}", liked.getUser().getId());
        log.info("좋아요 id : {}", liked.getId());
        assertThat(liked).isNotNull();
        assertThat(liked.getArticle().getId()).isEqualTo(1L);
        assertThat(liked.getUser().getId()).isEqualTo(1L);
        assertThat(liked.getId()).isEqualTo(5L);
    }

//    @Test
//    @DisplayName("좋아요 취소")
//    @Transactional
//    public void decreaseLikedCount() {
//        articleRepository.updateLikedCount(1L, -1);
//        Article article = articleRepository.findArticleById(1L).orElseThrow(NotFoundArticleException::new);
//        log.info("게시글 좋아요 취소 : {}", article.getLikedCount());
//        assertThat(article.getLikedCount()).isEqualTo(-1);
//    }

    @Test
    @DisplayName("좋아요 테이블 상태 변경")
    @Transactional
    public void deleteLikedTableTest() {
        likedRepository.deleteLiked(1L);
        Liked liked = likedRepository.findById(1L).orElseThrow();
        log.info("좋아요 상태 : {}", liked.getStatus());
        assertThat(liked.getStatus()).isEqualTo(Status.Like.INACTIVE);
    }

}