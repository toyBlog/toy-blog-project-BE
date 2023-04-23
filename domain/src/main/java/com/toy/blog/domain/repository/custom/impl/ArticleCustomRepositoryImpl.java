package com.toy.blog.domain.repository.custom.impl;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.Article;
import com.toy.blog.domain.repository.custom.ArticleCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.toy.blog.domain.entity.QArticle.article;
import static com.toy.blog.domain.entity.QUser.user;


@RequiredArgsConstructor
public class ArticleCustomRepositoryImpl implements ArticleCustomRepository {

    private final EntityManager entityManager;

//    private JPAQueryFactory queryFactory;
//
//    public ArticleCustomRepositoryImpl(EntityManager em) {
//        this.queryFactory = new JPAQueryFactory(em);
//    }

    /**
     * 게시글 목록 조회
     */
    @Override
    public List<Article> getArticleList(Integer page, Integer size) {
        return new JPAQuery<Article>(entityManager)
                .from(article)
                .where(article.status.eq(Status.Article.ACTIVE))
                .limit(size).offset(page)
                .fetch();
    }

    /**
     * id로 게시글 찾기
     */
    @Override
    public Optional<Article> findArticleById(Long id) {
        return Optional.ofNullable(new JPAQuery<Article>(entityManager)
                .from(article)
                .where(
                        article.id.eq(id),
                        article.status.eq(Status.Article.ACTIVE)
                )
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
    @Override
    public void editArticle(Long id, String title, String content) {
        new JPAUpdateClause(entityManager, article)
                .set(article.title, title)
                .set(article.content, content)
                .where(article.id.eq(id))
                .execute();
    }

    /**
     * 게시글 삭제
     */
    @Override
    public void deleteArticle(Long id) {
        new JPAUpdateClause(entityManager, article)
                .set(article.status, Status.Article.INACTIVE)
                .where(
                        article.id.eq(id),
                        article.status.eq(Status.Article.ACTIVE)
                )
                .execute();
    }

    /**
     * 게시글 좋아요 증가/취소
     */
    @Override
    public void updateLikedCount(Long id, Integer value) {
        new JPAUpdateClause(entityManager, article)
                .set(article.likedCount, article.likedCount.add(value))
                .where(article.id.eq(id))
                .execute();
    }

    /**
     * 팔로우한 친구의 게시글 목록 조회
     * To do 구현
     */
    @Override
    public List<Article> getFollowArticleList(List<Long> friendIdList, Pageable pageable) {
        return null;
//        return queryFactory
//                .select(article)
//                .from(article)
//                .join(article.user, user).fetchJoin()
//                .where(article.user.id.in(friendIdList))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .orderBy(article.createdAt.desc())
//                .fetch();
    }

}
