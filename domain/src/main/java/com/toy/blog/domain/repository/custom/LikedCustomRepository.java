package com.toy.blog.domain.repository.custom;

import com.toy.blog.domain.entity.Liked;

import java.util.Optional;

public interface LikedCustomRepository {

    Optional<Liked> findByArticleAndUser(Long id, Long userId);

    void deleteLiked(Long id);
}
