package com.toy.blog.domain.repository.article;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.blog.domain.entity.Article;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.toy.blog.domain.entity.QArticle.article;
import static com.toy.blog.domain.entity.QUser.user;

public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public ArticleRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Article> getFollowArticleList(List<Long> friendIdList, Pageable pageable) {

        return queryFactory
                .select(article)
                .from(article)
                .join(article.user, user).fetchJoin()
                .where(article.user.id.in(friendIdList))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(article.createdAt.desc())
                .fetch();
    }
}
