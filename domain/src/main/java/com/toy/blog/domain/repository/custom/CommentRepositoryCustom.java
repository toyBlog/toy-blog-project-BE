package com.toy.blog.domain.repository.custom;

import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryCustom {

    List<Comment> findByArticleAndStatus(Long id, Status.Comments status, Integer page, Integer size);

    Optional<Comment> findByIdAndStatus(Long id);
}
