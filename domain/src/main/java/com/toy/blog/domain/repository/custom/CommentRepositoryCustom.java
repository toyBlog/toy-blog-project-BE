package com.toy.blog.domain.repository.custom;

import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.Comment;
import com.toy.blog.domain.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryCustom {

    List<Comment> findByArticleIdAndStatusWithUser(Long articleId, Status.Comment status, Pageable pageable);

    long countByArticleIdAndStatus(Long articleId, Status.Comment status);

    Optional<Comment> findByIdAndStatus(Long id);
}
