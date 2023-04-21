package com.toy.blog.domain.repository;

import com.toy.blog.domain.entity.UserFriend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserFriendRepository extends JpaRepository<UserFriend, Long> {

    Optional<UserFriend> findByUserIdAndFriendId(Long userId, Long friendId);
}
