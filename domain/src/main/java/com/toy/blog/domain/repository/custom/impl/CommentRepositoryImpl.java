package com.toy.blog.domain.repository.custom.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.Comment;
import com.toy.blog.domain.repository.custom.CommentRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;

import static com.toy.blog.domain.common.Status.Article.ACTIVE;
import static com.toy.blog.domain.entity.QArticle.article;
import static com.toy.blog.domain.entity.QComment.comment;

public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private JPAQueryFactory queryFactory;


    public CommentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Comment> findByArticle(Long id, Integer page, Integer size) {
        return queryFactory.select(comment)
                .from(comment)
                .join(comment.article, article)
                .fetchJoin()
                .where(comment.status.eq(Status.Comments.ACTIVE)
                        .and(comment.article.id.eq(id)))
                .offset(page)
                .limit(size)
                .orderBy(article.createdAt.desc())
                .fetch();
    }
}
