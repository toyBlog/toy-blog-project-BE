package com.toy.blog.domain.repository.custom.impl;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.Article;
import com.toy.blog.domain.repository.custom.ArticleCustomRepository;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.toy.blog.domain.entity.QArticle.article;
import static com.toy.blog.domain.entity.QUser.user;


@RequiredArgsConstructor
public class ArticleCustomRepositoryImpl implements ArticleCustomRepository {

    private final EntityManager entityManager;

    /**
     * 게시글 목록 조회
     */
    public List<Article> findAllArticleBySearch(String title, String content, String nickname, Integer page, Integer size) {
        return new JPAQuery<Article>(entityManager)
                .from(article)
                .join(article.user, user)
                .where(article.title.like(title)
                        .or(article.content.like(content)
                                .or(article.user.nickname.like(nickname))))
                .limit(size).offset(page)
                .fetch();
    }

    /**
     * 팔로우한 친구의 게시글 목록 조회
     */
    @Override
    public List<Article> findAllFollowedArticleBySearch(String title, String content, String nickname, Integer page, Integer size) {
        return new JPAQuery<Article>(entityManager)
                .from(article)
                .join(article.user, user)
                .where(article.title.like(title)
                        .or(article.content.like(content)
                                .or(article.user.nickname.like(nickname))))
                .limit(size).offset(page)
                .fetch();
    }

    /**
     * id로 게시글 조회
     */
    public Optional<Article> findArticleWithUserBy(Long id) {
        return Optional.ofNullable(new JPAQuery<Article>(entityManager)
                .from(article)
                .join(article.user, user)
                .fetchJoin()
                .where(article.id.eq(id))
                .fetchOne());
    }

    /**
     * 조회수 증가
     */
    @Override
    public void updateViewCount(Long id) {
        new JPAUpdateClause(entityManager, article)
                .set(article.viewCount, article.viewCount.add(1))
                .where(article.id.eq(id))
                .execute();
    }

    /**
     * 게시글 수정
     */
    public void editArticle(Long id, Long userId, String title, String content) {
        new JPAUpdateClause(entityManager, article)
                .set(article.title, article.title.append(title))
                .set(article.content, article.content.append(content))
                .where(
                        article.id.eq(id),
                        article.user.id.eq(userId)
                )
                .execute();
    }

    /**
     * 게시글 삭제
     */
    public void deleteArticle(Long id) {
        new JPAUpdateClause(entityManager, article)
                .set(article.status, Status.Article.INACTIVE)
                .where(article.id.eq(id).and(article.status.eq(Status.Article.ACTIVE)))
                .execute();
    }


}
