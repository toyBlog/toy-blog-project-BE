package com.toy.blog.domain.repository.custom.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.UserFriend;
import com.toy.blog.domain.repository.custom.UserFriendRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;

import static com.toy.blog.domain.entity.QUserFriend.userFriend;

public class UserFriendRepositoryImpl implements UserFriendRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public UserFriendRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<UserFriend> findFollowList(Long userId) {

        return queryFactory.select(userFriend)
                .from(userFriend)
                .where(userFriend.status.eq(Status.UserFriend.FOLLOW), userFriend.user.id.eq(userId))
                .orderBy(userFriend.createdAt.desc())
                .fetch();
    }

    @Override
    public List<UserFriend> findFollowingList(Long friendId) {

        return queryFactory.select(userFriend)
                .from(userFriend)
                .where(userFriend.status.eq(Status.UserFriend.FOLLOW), userFriend.friendId.eq(friendId))
                .orderBy(userFriend.createdAt.desc())
                .fetch();
    }
}