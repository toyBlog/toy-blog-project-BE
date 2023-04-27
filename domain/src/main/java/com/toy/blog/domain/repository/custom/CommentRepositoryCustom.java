package com.toy.blog.domain.repository.custom;

import com.toy.blog.domain.entity.Comment;

import java.util.List;

public interface CommentRepositoryCustom {

    List<Comment> findByArticle(Long id,Integer page, Integer size);
}
