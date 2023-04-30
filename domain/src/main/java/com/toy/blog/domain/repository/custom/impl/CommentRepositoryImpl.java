package com.toy.blog.domain.repository.custom.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.Comment;
import com.toy.blog.domain.repository.custom.CommentRepositoryCustom;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.toy.blog.domain.entity.QArticle.article;
import static com.toy.blog.domain.entity.QComment.comment;
import static com.toy.blog.domain.entity.QUser.user;

public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private JPAQueryFactory queryFactory;


    public CommentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Comment> findByArticleIdAndStatusWithUser(Long articleId, Status.Comment status, Pageable pageable) {

        return queryFactory.select(comment)
                .from(comment)
                .join(comment.user, user).fetchJoin()
                .where(comment.status.eq(status), (comment.article.id.eq(articleId)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(comment.createdAt.desc())
                .fetch();
    }

    /**
     * --------------------------------------------------------------------------------------------------------------
     */

    @Override
    public long countByArticleIdAndStatus(Long articleId, Status.Comment status) {

        return queryFactory.select(comment)
                .from(comment)
                .where(comment.status.eq(status), (comment.article.id.eq(articleId)))
                .fetchCount();
    }

    /**
     * --------------------------------------------------------------------------------------------------------------
     */

    @Override
    public Optional<Comment> findByIdAndStatus(Long id) {
        return Optional.ofNullable(queryFactory.select(comment)
                .from(comment)
                .where(comment.id.eq(id)
                        .and(comment.status.eq(Status.Comment.ACTIVE)))
                .fetchOne());
    }
}
