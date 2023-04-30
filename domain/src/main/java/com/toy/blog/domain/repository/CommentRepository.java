package com.toy.blog.domain.repository;

import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.Comment;
import com.toy.blog.domain.repository.custom.CommentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

    @Modifying
    @Query("update Comment c set c.status=:status where c.article.id=:articleId")
    int updateStatusByArticleId(@Param("status") Status.Comment status, @Param("articleId") Long articleId);

    @Query("select c from Comment c join fetch c.user where c.id=:id and c.status=:status")
    Optional<Comment> findByIdAndStatusWithUser(@Param("id") Long id, @Param("status") Status.Comment status);

}
