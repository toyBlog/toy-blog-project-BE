package com.toy.blog.domain.repository.custom.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.Article;
import com.toy.blog.domain.entity.QArticle;
import com.toy.blog.domain.repository.custom.ArticleRepositoryCustom;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.toy.blog.domain.common.Status.Article.ACTIVE;
import static com.toy.blog.domain.entity.QArticle.article;
import static com.toy.blog.domain.entity.QUser.user;

public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public ArticleRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Article> findFollowArticleList(List<Long> friendIdList, Pageable pageable) {

        return queryFactory
                .select(article)
                .from(article)
                .join(article.user, user).fetchJoin()
                .where(article.user.id.in(friendIdList), article.status.eq(ACTIVE))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(article.createdAt.desc())
                .fetch();
    }

    /**
     * ---------------------------------------------------------------------------------------------------------------
     */

    @Override
    public long findFollowArticleListTotal(List<Long> friendIdList) {

        return queryFactory
                .select(article)
                .from(article)
                .where(article.user.id.in(friendIdList), article.status.eq(ACTIVE))
                .fetchCount();
    }

    /**---------------------------------------------------------------------------------------------------------------*/

    /**
     * 게시글 목록 조회
     */
    @Override
    public List<Article> findArticleList(Integer page, Integer size) {

        return queryFactory
                .select(article)
                .from(article)
                .where(article.status.eq(Status.Article.ACTIVE))
                .limit(size).offset(page)
                .fetch();
    }


    /**---------------------------------------------------------------------------------------------------------------*/

    /**
     * id로 게시글 찾기
     */

    @Override
    public Optional<Article> findArticleById(Long id) {

        Article article = queryFactory.select(QArticle.article)
                .from(QArticle.article)
                .where(
                        QArticle.article.id.eq(id),
                        QArticle.article.status.eq(ACTIVE)
                )
                .fetchOne();

        return Optional.ofNullable(article);
    }


    /**
     * 조회수 증가
     */
    @Override
    public void updateViewCount(Long id) {

        queryFactory.update(article)
                .set(article.viewCount, article.viewCount.add(1))
                .where(article.id.eq(id))
                .execute();
    }


    /**---------------------------------------------------------------------------------------------------------------*/

    /**
     * 게시글 수정
     */
    @Override
    public void updateArticle(Long id, String title, String content) {

        queryFactory.update(article)
                .set(article.title, title)
                .set(article.content, content)
                .where(article.id.eq(id))
                .execute();
    }


    /**---------------------------------------------------------------------------------------------------------------*/

    /**
     * 게시글 삭제
     */
    @Override
    public void inactiveArticle(Long id) {

        queryFactory.update(article)
                .set(article.status, Status.Article.INACTIVE)
                .where(article.id.eq(id))
                .execute();
    }


    /**---------------------------------------------------------------------------------------------------------------*/

    /**
     * 게시글 좋아요 증가/취소
     */
    @Override
    public void updateLikedCount(Long id, Integer value) {

        queryFactory.update(article)
                .set(article.likedCount, article.likedCount.add(value))
                .where(article.id.eq(id))
                .execute();
    }
}


    /**---------------------------------------------------------------------------------------------------------------*/

