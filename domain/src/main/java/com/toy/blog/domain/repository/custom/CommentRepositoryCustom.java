package com.toy.blog.domain.repository.custom;

import com.toy.blog.domain.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryCustom {

    List<Comment> findByArticleWithStatus(Long id, Integer page, Integer size);

    Optional<Comment> findByIdAndStatus(Long id);
}
