package com.toy.blog.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserFriend is a Querydsl query type for UserFriend
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserFriend extends EntityPathBase<UserFriend> {

    private static final long serialVersionUID = 1255105977L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserFriend userFriend = new QUserFriend("userFriend");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> friendId = createNumber("friendId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.toy.blog.domain.common.Status.UserFriend> status = createEnum("status", com.toy.blog.domain.common.Status.UserFriend.class);

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> updatedAt = _super.updatedAt;

    public final QUser user;

    public QUserFriend(String variable) {
        this(UserFriend.class, forVariable(variable), INITS);
    }

    public QUserFriend(Path<? extends UserFriend> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserFriend(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserFriend(PathMetadata metadata, PathInits inits) {
        this(UserFriend.class, metadata, inits);
    }

    public QUserFriend(Class<? extends UserFriend> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

