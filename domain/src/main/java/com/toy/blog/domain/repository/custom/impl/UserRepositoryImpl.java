package com.toy.blog.domain.repository.custom.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.blog.domain.entity.User;
import com.toy.blog.domain.repository.custom.UserRepositoryCustom;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.toy.blog.domain.common.Status.User.ACTIVE;
import static com.toy.blog.domain.entity.QUser.user;

public class UserRepositoryImpl implements UserRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<User> findUserList(List<Long> userIdList, Pageable pageable) {

        return queryFactory.select(user)
                           .from(user)
                           .where(user.id.in(userIdList) , user.status.eq(ACTIVE))
                           .offset(pageable.getOffset())
                           .limit(pageable.getPageSize())
                           .orderBy(user.createdAt.desc())
                           .fetch();
    }

    @Override
    public long findUserListCount(List<Long> userIdList) {

        return queryFactory.select(user)
                           .from(user)
                           .where(user.id.in(userIdList), user.status.eq(ACTIVE))
                           .fetchCount();
    }
}
