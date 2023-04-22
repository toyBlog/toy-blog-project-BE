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
    
    public  Optional<Article> findArticleWithUserBy(Long id) {
        return Optional.ofNullable(new JPAQuery<Article>(entityManager)
                .from(article)
                .join(article.user, user)
                .fetchJoin()
                .where(article.id.eq(id))
                .fetchOne());
    }

    public List<Article> findArticlebySearchColum(String title, String content,String nickname,Integer page, Integer size) {
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
     * 조회수 증가
     */
    @Override
    public void updateViewCount(Long id) {
        new JPAUpdateClause(entityManager, article)
                .set(article.viewCount, article.viewCount.add(1))
                .where(article.id.eq(id))
                .execute();
    }

    public List<Article> findArticleListByOrderByIdDesc(Integer page, Integer size) {
        return new JPAQuery<Article>(entityManager)
                .from(article)
                .where(article.status.eq(Status.Article.ACTIVE))
                .limit(size).offset(page)
                .fetch();
    }
    
    public long editArticle(Long id, Long userId, String title, String content) {
        return new JPAUpdateClause(entityManager, article)
                .set(article.title, article.title.append(title))
                .set(article.content, article.content.append(content))
                .where(
                        article.id.eq(id),
                        article.user.id.eq(userId)
                )
                .execute();
    }
    
    public long deleteArticle(Long id) {
        return new JPAUpdateClause(entityManager, article)
                .set(article.status, Status.Article.INACTIVE)
                .where(article.id.eq(id).and(article.status.eq(Status.Article.ACTIVE)))
                .execute();
    }
    


}
