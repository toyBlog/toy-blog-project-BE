package com.toy.blog.domain.repository.custom.impl;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.Liked;
import com.toy.blog.domain.repository.custom.LikedCustomRepository;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

import java.util.Optional;

import static com.toy.blog.domain.entity.QLiked.liked;

@RequiredArgsConstructor
public class LikedCustomRepositoryImpl implements LikedCustomRepository {

    private final EntityManager entityManager;

    /**
     * 글 id와 사용자 id로 Liked 찾기
     */
    @Override
    public Optional<Liked> findByArticleAndUser(Long id, Long userId) {
        return Optional.ofNullable(new JPAQuery<Liked>(entityManager)
                .from(liked)
                .where(
                        liked.article.id.eq(id),
                        liked.user.id.eq(userId),
                        liked.status.eq(Status.Like.ACTIVE)
                )
                .fetchOne());
    }

    /**
     * 좋아요 삭제
     */
    @Override
    public void deleteLiked(Long id) {
        new JPAUpdateClause(entityManager, liked)
                .set(liked.status, Status.Like.INACTIVE)
                .where(
                        liked.id.eq(id),
                        liked.status.eq(Status.Like.ACTIVE)
                )
                .execute();
    }
}
