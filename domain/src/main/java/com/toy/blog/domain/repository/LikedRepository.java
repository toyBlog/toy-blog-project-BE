package com.toy.blog.domain.repository;

import com.toy.blog.domain.entity.Liked;
import com.toy.blog.domain.repository.custom.LikedCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedRepository extends JpaRepository<Liked, Long>, LikedCustomRepository {
}
