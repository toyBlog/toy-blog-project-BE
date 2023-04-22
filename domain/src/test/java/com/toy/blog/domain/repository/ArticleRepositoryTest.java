package com.toy.blog.domain.repository;

import com.toy.blog.domain.entity.Article;
import com.toy.blog.domain.repository.article.ArticleRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleRepositoryTest {

    @Autowired
    ArticleRepository articleRepository;

    @Test
    @DisplayName("UserId로 게시글 찾기")
    public void findArticleByUserId() {
        Long id = 1L;
        String title = "title";
        String content = "content1111111";

        articleRepository.save(Article.builder()
                .id(id)
                .title(title)
                .content(content)
                .viewCount(0)
                .likedCount(0)
                .build());

        List<Article> articleList = articleRepository.findAll();

        Article article = articleList.get(0);
        assertThat(article.getId()).isEqualTo(id);
        assertThat(article.getTitle()).isEqualTo(title);
        assertThat(article.getContent()).isEqualTo(content);
    }

}