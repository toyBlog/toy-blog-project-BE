package com.toy.blog.domain.repository.custom.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.Article;
import com.toy.blog.domain.entity.Liked;
import com.toy.blog.domain.entity.QArticle;
import com.toy.blog.domain.entity.QLiked;
import com.toy.blog.domain.repository.custom.LikedRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static com.toy.blog.domain.entity.QArticle.article;
import static com.toy.blog.domain.entity.QLiked.liked;

@RequiredArgsConstructor
public class LikedRepositoryImpl implements LikedRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public LikedRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }



    /** --------------------------------------------------------------------------------------------------------------*/

    /**
     * 글 id와 사용자 id로 Liked 찾기
     */
    @Override
    public Optional<Liked> findByArticleAndUser(Long id, Long userId) {

        Liked liked = queryFactory
                .select(QLiked.liked)
                .from(QLiked.liked)
                .where(
                        QLiked.liked.article.id.eq(id),
                        QLiked.liked.user.id.eq(userId),
                        QLiked.liked.status.eq(Status.Like.ACTIVE)
                )
                .fetchOne();

        return Optional.ofNullable(liked);
    }

    /** --------------------------------------------------------------------------------------------------------------*/

    /**
     * 좋아요 삭제
     */
    @Override
    public void deleteLiked(Long id) {

        queryFactory.update(liked)
                .set(liked.status, Status.Like.INACTIVE)
                .where(
                        liked.id.eq(id),
                        liked.status.eq(Status.Like.ACTIVE)
                )
                .execute();
    }

    /** --------------------------------------------------------------------------------------------------------------*/

    /**
     * 이 userId의 User가 좋아요 한 글 목록 조회
     * */
    @Override
    public List<Article> findArticleListByUserId(Long userId, Pageable pageable) {

        return queryFactory.select(article)
                    .from(liked)
                    .join(liked.article, article)
                    .where(liked.user.id.eq(userId) , liked.status.eq(Status.Like.ACTIVE) , article.status.eq(Status.Article.ACTIVE))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(liked.createdAt.desc())
                    .fetch();
    }


    /** --------------------------------------------------------------------------------------------------------------*/

    /**
     * 이 userId의 User가 좋아요 한 글 목록개수 조회
     * */

    @Override
    public long countArticleListByUserId(Long userId) {

       return queryFactory.select(article)
                          .from(liked)
                          .join(liked.article, article)
                          .where(liked.user.id.eq(userId) , liked.status.eq(Status.Like.ACTIVE) , article.status.eq(Status.Article.ACTIVE))
                          .fetchCount();
    }
}
