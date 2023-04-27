package com.toy.blog.domain.repository;

import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.Article;
import com.toy.blog.domain.entity.Comment;
import com.toy.blog.domain.repository.custom.CommentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
}
