package com.toy.blog.domain.repository.custom.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.Article;
import com.toy.blog.domain.entity.Comment;
import com.toy.blog.domain.repository.custom.ArticleRepositoryCustom;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.toy.blog.domain.common.Status.Article.ACTIVE;
import static com.toy.blog.domain.entity.QArticle.article;
import static com.toy.blog.domain.entity.QComment.comment;
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
    public long countFollowArticleList(List<Long> friendIdList) {

        return queryFactory
                .select(article)
                .from(article)
                .where(article.user.id.in(friendIdList), article.status.eq(ACTIVE))
                .fetchCount();
    }

    /**
     * ---------------------------------------------------------------------------------------------------------------
     */

    public List<Article> findByTitleOrContent(String keyword, Integer page, Integer size) {

        return queryFactory.select(article)
                .from(article)
                .where(searchCond(keyword), article.status.eq(ACTIVE))
                .offset(page)
                .limit(size)
                .orderBy(article.createdAt.desc())
                .fetch();
    }

    private BooleanExpression searchCond(String keyword) {

        if (keyword.length() == 0) {
            return null;
        } else {
            return article.title.like("%" + keyword + "%").or(article.content.like("%" + keyword + "%"));
        }
    }

    /**
     * ---------------------------------------------------------------------------------------------------------------
     */

    @Override
    public long countByTitleOrContent(String keyword) {

        return queryFactory.select(article)
                .from(article)
                .where(searchCond(keyword), article.status.eq(ACTIVE))
                .fetchCount();
    }

    @Override
    public Optional<Article> findByIdWithComment(Long id, Integer page, Integer size) {

        JPQLQuery<Article> query = queryFactory.selectFrom(article)
                .leftJoin(article.commentList, comment)
                .where(article.id.eq(id).and(article.status.eq(ACTIVE)).and(comment.status.eq(Status.Comments.ACTIVE)));

        Article result = query.fetchOne();
        if (result != null) {
            JPQLQuery<Comment> commentQuery = queryFactory.selectFrom(comment)
                    .where(comment.article.eq(result).and(comment.status.eq(Status.Comments.ACTIVE)))
                    .orderBy(comment.createdAt.desc())
                    .offset(page)
                    .limit(size);
            result.setCommentList(commentQuery.fetch());
        }

        return Optional.ofNullable(result);
    }


    /**
     * ---------------------------------------------------------------------------------------------------------------
     */


}
