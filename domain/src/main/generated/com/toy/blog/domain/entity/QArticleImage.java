package com.toy.blog.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArticleImage is a Querydsl query type for ArticleImage
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QArticleImage extends EntityPathBase<ArticleImage> {

    private static final long serialVersionUID = 1047216501L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArticleImage articleImage = new QArticleImage("articleImage");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QArticle article;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath path = createString("path");

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> updatedAt = _super.updatedAt;

    public QArticleImage(String variable) {
        this(ArticleImage.class, forVariable(variable), INITS);
    }

    public QArticleImage(Path<? extends ArticleImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArticleImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArticleImage(PathMetadata metadata, PathInits inits) {
        this(ArticleImage.class, metadata, inits);
    }

    public QArticleImage(Class<? extends ArticleImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.article = inits.isInitialized("article") ? new QArticle(forProperty("article"), inits.get("article")) : null;
    }

}

